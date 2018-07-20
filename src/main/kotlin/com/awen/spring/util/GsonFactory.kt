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

import com.google.gson.*
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object GsonFactory {

    fun getDefault(): Gson {
        return Gson()
    }

    fun getCustomWhitDateFormat(): Gson {

        return GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
    }

    fun getCustomWithAdapterDefaultDateFormat(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("yyyy-MM-dd")
        gsonBuilder.registerTypeAdapter(Date::class.java, DateDeserializer("yyyy-MM-dd"))

        return gsonBuilder.create()
    }

    fun getCustomWithAdapterDetailDateFormat(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss")
        gsonBuilder.registerTypeAdapter(Date::class.java, DateDeserializer("yyyy-MM-dd HH:mm:ss"))
        val gson = gsonBuilder.create()

        return gsonBuilder.create()
    }

    internal class DateDeserializer(private val format: String) : JsonDeserializer<Date> {

        @Throws(JsonParseException::class)
        override fun deserialize(element: JsonElement, arg1: Type, arg2: JsonDeserializationContext): Date? {
            val date = element.asString

            val formatter = SimpleDateFormat(format)
            formatter.timeZone = TimeZone.getTimeZone("GMT+8")

            return try {
                formatter.parse(date)
            } catch (e: ParseException) {
                //System.err.println("Failed to parse Date due to:", e);
                null
            }

        }
    }

}