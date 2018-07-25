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

package com.awen.spring.cache

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.stereotype.Component

@Component
@PropertySources(PropertySource(value = ["classpath:cache-client.properties"], ignoreResourceNotFound = true))
class OcsClientFactory: OcsKeyManager {

    private val logger = LoggerFactory.getLogger(OcsClientFactory::class.java)

    @Value("\${cache.servers}")
    private lateinit var ALIOCS_HOST: String

    @Volatile
    private var cache: MultiCacheClient? = null

    @Throws(Exception::class)
    private fun getClient(): MultiCacheClient? {
        if (cache == null) {
            synchronized(this) {
                if (cache == null) {
                    try {
                        cache = MultiCacheClient(ALIOCS_HOST)
                    } catch (e: Exception) {
                        logger.info("MemcachedClient  ERROR : {}", e.message)
                        close()
                        throw Exception("MemcachedClient  ERROR : " + e.message)
                    }

                }
            }
        }
        return cache
    }

    override fun close() {
        cache!!.close()
    }

    @Throws(Exception::class)
    override
    operator fun set(key: String, exp: Int, value: Any) {
        getClient()!![key, exp] = value
    }

    @Throws(Exception::class)
    override
    operator fun get(key: String): Any? {
        // TODO Auto-generated method stub
        return getClient()!![key]
    }


}