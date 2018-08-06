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

package com.awen.spring.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import javax.annotation.Resource


@Service
class RedisService {

    private val BASE_KEY = "BASE_KEY_%s"

    @Resource
    private  lateinit var stringRedisTemplate: StringRedisTemplate

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    fun set(key: String, value: String): Boolean {
        var result = false
        try {
            val operations = stringRedisTemplate.opsForValue()
            operations.set(key, value)
            result = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    fun set(key: String, value: String, expireTime: Long): Boolean {
        var result = false
        try {
            val operations = stringRedisTemplate.opsForValue()
            operations.set(key, value)
            stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS)
            result = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    /**
     * 批量删除对应的value
     * @param keys
     */
    fun remove(vararg keys: String) {
        for (key in keys) {
            remove(key)
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    fun removePattern(pattern: String) {
        val keys = stringRedisTemplate.keys(pattern)
        if (keys.size > 0)
            stringRedisTemplate.delete(keys)
    }

    /**
     * 删除对应的value
     * @param key
     */
    fun remove(key: String) {
        if (exists(key)) {
            stringRedisTemplate.delete(key)
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    fun exists(key: String): Boolean {
        return stringRedisTemplate.hasKey(key)
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    fun get(key: String): String? {
        var result: String?
        val operations = stringRedisTemplate.opsForValue()
        result = operations.get(key)
        return result
    }

}