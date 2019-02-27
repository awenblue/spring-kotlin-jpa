package com.awen.spring.service

import com.awen.spring.common.param.BasePageParam
import com.awen.spring.model.base.BaseModel
import com.awen.spring.repository.entity.BaseEntity
import com.awen.spring.repository.jpa.BaseRepository
import com.awen.spring.service.data.BasePageList

interface BaseModelDataService<T: BaseModel<Y>, Y: BaseEntity> {

    fun getList(pageParam: BasePageParam, repository: BaseRepository<Y>, clazz: Class<T>): List<T>

    fun getList(pageParam: BasePageParam, repository: BaseRepository<Y>, clazz: Class<T>, basePage: BasePageList<Y>): List<T>

    fun getList(repository: BaseRepository<Y>, clazz: Class<T>): List<T>

    fun getListSort(repository: BaseRepository<Y>, clazz: Class<T>, column: String?, desc: Boolean?): List<T>

    fun getById(id: Long, repository: BaseRepository<Y>, clazz: Class<T>): T

    fun getByIds(ids: List<Long>, repository: BaseRepository<Y>, clazz: Class<T>): List<T>

    fun addModel(model: T, repository: BaseRepository<Y>): T

    fun addModel(model: List<T>, repository: BaseRepository<Y>): List<T>

    fun updateModel(model: T, repository: BaseRepository<Y>): T

    fun deleteModel(model: T, repository: BaseRepository<Y>)

}