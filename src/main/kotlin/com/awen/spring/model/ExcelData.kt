package com.awen.spring.model

import java.io.Serializable

data class ExcelData (
        // 表头
        val titles: ArrayList<String>,

        // 数据
        val rows: ArrayList<ArrayList<Any>>,

        // 页签名称
        val name: String

): Serializable