package com.awen.spring.service

import com.awen.spring.common.param.BasePageParam
import com.awen.spring.model.base.BaseModel
import com.awen.spring.repository.entity.BaseEntity
import com.awen.spring.service.data.BasePageList

interface BaseModelDataService<T: BaseModel<Y>, Y: BaseEntity> {

    fun getList(pageParam: BasePageParam): List<T>

    fun getList(pageParam: BasePageParam, basePage: BasePageList<Y>): List<T>

    fun getList(): List<T>

    fun getListSort(column: String?, desc: Boolean?): List<T>

    fun getById(id: Long): T

    fun getByIds(ids: List<Long>): List<T>

    fun addOrUpdateModel(model: T): T

    fun addOrUpdateModels(model: List<T>): List<T>

    fun deleteModel(model: T)

}