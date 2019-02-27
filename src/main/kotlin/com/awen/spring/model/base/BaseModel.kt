package com.awen.spring.model.base

import com.awen.spring.repository.entity.BaseEntity
import java.util.*

abstract class BaseModel<T: BaseEntity> {

    var id: Long = -1L
    var createDate: Date = Date()
    var updateDate: Date = Date()

    abstract fun init(entity: T)

    abstract fun toEntity(): T

    protected fun initBaseModel(t: T) {
        id = t.id
        createDate = t.createDate
        updateDate = t.updateDate
    }

    protected fun initBaseEntity(t: T): T {
        if (id <= 0) id = -1L

        t.id = id
        t.createDate = createDate
        t.updateDate = updateDate

        return t
    }

    fun exist(): Boolean = id > 0L

    fun notExist(): Boolean = !exist()

    open fun getSimpleName(): String {
        return ""
    }

    protected fun splitFilter(value: String): Set<String> {
        return value.split(",").filter { it.isNotBlank() }.toSet()
    }
}