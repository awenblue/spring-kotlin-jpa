/*
 *
 *     Copyright 2018 The awen_blue Authors
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

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