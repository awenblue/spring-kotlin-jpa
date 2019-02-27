package com.awen.spring.common.param

interface PageParam {

    fun getPageIndex(): Int

    fun getSize(): Int

}


data class BasePageParam(
        var page: Int = 0,
        var pageSize: Int = 5,
        var extraId: Long = 0,
        var filter: String?,
        var sortColumn: Int?,
        var desc: Boolean?,
        var status: Int = 0
): PageParam, BaseUserParam() {

    override fun getPageIndex(): Int {
        return page
    }

    override fun getSize(): Int {
        return pageSize
    }

}