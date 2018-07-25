package com.awen.spring.controller

import com.awen.spring.model.ExcelData
import com.awen.spring.util.ExportExcelUtils
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletResponse


@RestController
class DownloadController {

    private val logger = LoggerFactory.getLogger(DownloadController::class.java)

    @GetMapping("/abc")
    fun downloadExcel(response: HttpServletResponse) {

        val titles = arrayListOf<String>()
        titles.add("a1")
        titles.add("a2")
        titles.add("a3")

        val rows = arrayListOf<ArrayList<Any>>()
        val row = arrayListOf<Any>()
        row.add("11111111111")
        row.add("22222222222")
        row.add("3333333333")
        rows.add(row)


        val data = ExcelData(titles, rows, "hello")

        ExportExcelUtils.exportExcel(response, "a.xlsx", data)

    }

}