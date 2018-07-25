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

import net.spy.memcached.MemcachedClient
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.InetSocketAddress
import java.util.concurrent.ConcurrentHashMap

class MultiCacheClient {

    private val logger = LoggerFactory.getLogger(MultiCacheClient::class.java)
    private val ipList: Array<String>
    private val clientMap = ConcurrentHashMap<String, MemcachedClient>()

    @Throws(Exception::class)
    constructor(targets: String) {
        this.ipList = targets.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (ipList.isEmpty())
            throw Exception("Memcached 地址配置错误")

        initClients()
    }

    @Throws(IOException::class)
    private fun initClients() {
        for (str in ipList) {
            initClient(str)
        }
    }

    @Throws(IOException::class)
    private fun initClient(ip: String): MemcachedClient {
        val split = ip.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val cache = MemcachedClient(InetSocketAddress(split[0], Integer.parseInt(split[1])))
        clientMap[ip] = cache

        return cache
    }

    @Synchronized
    fun close() {
        for (ip in ipList) {
            val client = clientMap[ip]!!
            close(client)
        }
    }

    private fun close(client: MemcachedClient) {
        try {
            client.shutdown()
        } catch (e: Exception) {
            e.printStackTrace()
            logger.error("关闭缓存错误：", e)
        }

    }

    @Synchronized
    operator fun set(key: String, exp: Int, value: Any) {
        for (ip in ipList) {
            var client = clientMap[ip]!!
            try {
                set(client, key, exp, value)
                continue
            } catch (e: Exception) {
                e.printStackTrace()
                logger.error("设置缓存错误：", e)
            }

            close(client)
            try {
                client = initClient(ip)
                set(client, key, exp, value)
            } catch (e: Exception) {
                e.printStackTrace()
                logger.error("缓存set错误，重连缓存错误：", e)
            }

        }
    }

    @Throws(Exception::class)
    private operator fun set(client: MemcachedClient, key: String, exp: Int, value: Any) {
        //执行set操作，向缓存中存数据 ,600s
        val future = client.set(key, exp, value)
        future.get()  //  确保之前(mc.set())操作已经结束
    }

    @Synchronized
    @Throws(Exception::class)
    operator fun get(key: String): Any? {
        // TODO Auto-generated method stub
        var value: Any? = null
        for (ip in ipList) {
            val client = clientMap[ip]!!
            try {
                value = get(client, key)
                return value
            } catch (e: Exception) {
                e.printStackTrace()
                logger.error("读取缓存错误：", e)
                continue
            }

        }
        return value
    }

    private fun get(client: MemcachedClient, key: String): Any? {
        return client.get(key)
    }



}