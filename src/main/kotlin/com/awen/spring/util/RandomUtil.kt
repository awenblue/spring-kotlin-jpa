package com.awen.spring.util

object RandomUtil {

    fun generateValidateCode(): String {

        val tmp = (1000000 * Math.random()).toInt()

        return if (tmp + 100000 >= 1000000) tmp.toString() else (tmp + 100000).toString()
    }

}


fun main(args: Array<String>) {
    val map = hashMapOf<String, String>()
    for (i in 0..999) {
        val validateCode = RandomUtil.generateValidateCode()
        println(validateCode)

        if (map[validateCode] != null) {
            println("----------------------------------------------------------------------------------")
        }
        map[validateCode] = validateCode
    }
    println(map.size)
}