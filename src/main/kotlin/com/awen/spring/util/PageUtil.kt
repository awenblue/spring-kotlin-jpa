package com.awen.spring.util

import com.awen.spring.common.param.PageParam
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

object PageUtil {


    fun getRowBounds(param: PageParam, sort: Sort?): PageRequest {
        var size = param.getSize()
        var pageIndex = param.getPageIndex()
        if (size < 1) {
            size = 10
        } else if (size > 20) {
            size = 20
        }
        if (pageIndex < 0) {
            pageIndex = 0
        }
        return if (sort != null) PageRequest.of(pageIndex, size, sort) else PageRequest.of(pageIndex, size)
    }
}