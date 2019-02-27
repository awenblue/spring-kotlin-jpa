package com.awen.spring.service.data

import com.awen.spring.common.param.BasePageParam
import com.awen.spring.model.base.BaseModel
import com.awen.spring.repository.entity.BaseEntity
import com.awen.spring.repository.jpa.BaseRepository
import com.awen.spring.service.BaseModelDataService
import com.awen.spring.util.PageUtil
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import java.util.*
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

abstract class BaseDataService<T: BaseModel<Y>, Y: BaseEntity>: BaseModelDataService<T, Y> {

    override fun getList(repository: BaseRepository<Y>, clazz: Class<T>): List<T> {

        return getListSort(repository, clazz, null, null)
    }

    override fun getListSort(repository: BaseRepository<Y>, clazz: Class<T>, column: String?, desc: Boolean?): List<T> {

        var asc = Sort.Direction.ASC
        desc?.also {
            asc = Sort.Direction.DESC
        }

        return if (column == null) {
            repository.findAll()
        } else {
            repository.findAll(Sort.by(asc, column))
        }.map {
            val model = newInstance(clazz)
            model.init(it)
            model
        }
    }

    override fun getList(pageParam: BasePageParam, repository: BaseRepository<Y>, clazz: Class<T>): List<T> {

        return getList(pageParam, repository, clazz, BasePageList())
    }

    override fun getList(pageParam: BasePageParam, repository: BaseRepository<Y>, clazz: Class<T>, basePage: BasePageList<Y>): List<T> {
        val column = basePage.getSortColumn(pageParam)

        var asc = Sort.Direction.ASC
        pageParam.desc?.also {
            asc = Sort.Direction.DESC
        }

        val sort = if (column == null) null else Sort.by(asc, column)

        val rowBounds = PageUtil.getRowBounds(pageParam, sort)

        val specification = Specification<Y> { root, _, builder ->
            val predicates = ArrayList<Predicate>()

            basePage.predicatesConfig(predicates, root, builder, pageParam)

            var toTypedArray = predicates.toTypedArray()
            builder.and(*toTypedArray)
        }

        val findAll = repository.findAll(specification, rowBounds)

        pageParam.page = findAll.totalPages
        pageParam.pageSize = findAll.totalElements.toInt()

        return findAll.content.map { entity ->
            val model = newInstance(clazz)
            model.init(entity)
            model
        }
    }

    override fun getById(id: Long, repository: BaseRepository<Y>, clazz: Class<T>): T {

        val model = newInstance(clazz)

        repository.findById(id).ifPresent {
            model.init(it)
        }

        return model
    }

    override fun getByIds(ids: List<Long>, repository: BaseRepository<Y>, clazz: Class<T>): List<T> {

        return repository.findAllById(ids).map {
            val model = newInstance(clazz)
            model.init(it)

            model
        }
    }

    override fun addModel(model: T, repository: BaseRepository<Y>): T {
        if (model.exist()) {
            model.updateDate = Date()
        }

        val save = repository.save(model.toEntity())
        model.id = save.id

        return model
    }

    override fun addModel(model: List<T>, repository: BaseRepository<Y>): List<T> {
        val date = Date()
        val map = model.map {
            if (it.exist()) {
                it.updateDate = date
            }
            it.toEntity()
        }

        repository.saveAll(map)

        return model
    }

    override fun updateModel(model: T, repository: BaseRepository<Y>): T {
        model.updateDate = Date()
        repository.save(model.toEntity())

        return model
    }

    override fun deleteModel(model: T, repository: BaseRepository<Y>) {
        repository.delete(model.toEntity())
    }

    private fun newInstance(clazz: Class<T>): T {

        return clazz.getDeclaredConstructor().newInstance()
    }
}

open class BasePageList<Y: BaseEntity> {

    open fun getSortColumn(pageParam: BasePageParam): String? {
        return null
    }

    open fun predicatesConfig(predicates: ArrayList<Predicate>, root: Root<Y>,
                                        criteriaBuilder: CriteriaBuilder, pageParam: BasePageParam) {
    }
}