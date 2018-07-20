/*
 * Copyright (c) 2018 The awen_blue Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.awen.spring.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object MD5Util {

    fun MD5(sourceStr: String): String {
        var result = ""
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(sourceStr.toByteArray())
            val b = md.digest()
            var i: Int
            val buf = StringBuffer("")
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0)
                    i += 256
                if (i < 16)
                    buf.append("0")
                buf.append(Integer.toHexString(i))
            }
            result = buf.toString()
            println("MD5($sourceStr,32) = $result")
            println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24))
        } catch (e: NoSuchAlgorithmException) {
            println(e)
        }

        return result
    }

}