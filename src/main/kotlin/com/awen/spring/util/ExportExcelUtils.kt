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

package com.awen.spring.util

import com.awen.spring.model.ExcelData
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.OutputStream
import java.net.URLEncoder
import javax.servlet.http.HttpServletResponse

/**
 * https://github.com/xiaopotian1990/SpringBootExcel/blob/master/src/main/java/com/xiaopotian/Controller/ExportExcelUtils.java
 */
object ExportExcelUtils {

    @Throws(Exception::class)
    fun exportExcel(response: HttpServletResponse, fileName: String, data: ExcelData) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel")
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"))
        exportExcel(data, response.outputStream)
    }

    @Throws(Exception::class)
    private fun exportExcel(data: ExcelData, out: OutputStream) {

        val wb = XSSFWorkbook()
        wb.use { wb ->
            var sheetName: String? = data.name
            if (null == sheetName) {
                sheetName = "Sheet1"
            }
            val sheet = wb.createSheet(sheetName)
            writeExcel(wb, sheet, data)

            wb.write(out)
        }
    }

    private fun writeExcel(wb: XSSFWorkbook, sheet: Sheet, data: ExcelData) {

        var rowIndex = writeTitlesToExcel(wb, sheet, data.titles)
        writeRowsToExcel(wb, sheet, data.rows, rowIndex)
        autoSizeColumns(sheet, data.titles.size + 1)

    }

    private fun writeTitlesToExcel(wb: XSSFWorkbook, sheet: Sheet, titles: List<String>): Int {
        var rowIndex = 0

        val titleFont = wb.createFont()
        titleFont.fontName = "simsun"
        titleFont.bold = true
        // titleFont.setFontHeightInPoints((short) 14);
        titleFont.color = IndexedColors.BLACK.index


        val titleRow = sheet.createRow(rowIndex)
        // titleRow.setHeightInPoints(25);

        for ((colIndex, field) in titles.withIndex()) {
            val cell = titleRow.createCell(colIndex)
            cell.setCellValue(field)
        }

        rowIndex++
        return rowIndex
    }

    private fun writeRowsToExcel(wb: XSSFWorkbook, sheet: Sheet, rows: List<List<Any>>, rowIndex: Int): Int {
        var rowIndex = rowIndex

        val dataFont = wb.createFont()
        dataFont.fontName = "simsun"
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.color = IndexedColors.BLACK.index

        for (rowData in rows) {
            val dataRow = sheet.createRow(rowIndex)
            // dataRow.setHeightInPoints(25);

            for ((colIndex, cellData) in rowData.withIndex()) {
                val cell = dataRow.createCell(colIndex)
                cell.setCellValue(cellData.toString())

            }
            rowIndex++
        }
        return rowIndex
    }

    private fun autoSizeColumns(sheet: Sheet, columnNumber: Int) {

        for (i in 0 until columnNumber) {
            val orgWidth = sheet.getColumnWidth(i)
            sheet.autoSizeColumn(i, true)
            val newWidth = (sheet.getColumnWidth(i) + 100) as Int
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth)
            } else {
                sheet.setColumnWidth(i, orgWidth)
            }
        }
    }



}