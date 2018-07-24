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

package com.awen.spring.config

import com.awen.spring.common.constant.ErrorType
import com.awen.spring.util.ResultUtil
import com.awen.spring.util.GsonFactory
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.io.IOException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Aspect
@Configuration
class LogAspect {


    internal var LogTemplate = "{ip:%s,url:%s,param:%s,return:%s,cost:%.3fms}"

    internal var ErrTemplate = "{ip:%s,url:%s,param:%s,return:%s,cost:%.3fms}"

    //本地异常日志记录对象
    private val logger = LoggerFactory.getLogger(LogAspect::class.java)

    //Controller层切点  http://www.cnblogs.com/qinyubin/p/4075466.html
    @Pointcut("execution(* com..controller.*.*(..))")
    fun controllerAspect() {
    }


    /**
     * 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     * @throws IOException
     */
    @Around("controllerAspect()")
    @Throws(IOException::class)
    fun doArround(joinPoint: ProceedingJoinPoint): Any {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request

        var info = ""
        var result: Any
        var params = ""
        val ip = getIpAddr(request)
        val url = request.requestURL
        val start = System.nanoTime()


        try {
            params = paramsToString(joinPoint)
            result = joinPoint.proceed()

            val cost = (System.nanoTime() - start) * 1.0 / 1E6
            info = String.format(LogTemplate, ip, url, params, GsonFactory.getCustomWhitDateFormat().toJson(result), cost)

            logger.debug(info)
        } catch (e: Throwable) {
            e.printStackTrace()

            result = ResultUtil.failed(ErrorType.ERROR_NO_DATA)
            try {
                val cost = (System.nanoTime() - start) * 1.0 / 1E6
                info = String.format(ErrTemplate, ip, url, params, GsonFactory.getCustomWhitDateFormat().toJson(result), cost)
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

            logger.error(info, e)
        }

        return result
    }

    fun getIpAddr(request: HttpServletRequest): String? {
        var ip: String? = request.getHeader("x-forwarded-for")
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }
        return ip
    }

    private fun paramsToString(joinPoint: ProceedingJoinPoint): String {
        var params = StringBuffer("")
        try {
            if (joinPoint.args != null && joinPoint.args.isNotEmpty()) {
                for (i in 0 until joinPoint.args.size) {
                    val arg: String
                    val obj = joinPoint.args[i]

                    arg = if (obj is MultipartHttpServletRequest)
                        "{\"" + (obj.parameterMap["postJsonData"]?.get(0) ?: "") + "\"}"
                    else if (obj is ServletRequest || obj is ServletResponse || obj is ServletRequest)
                        continue
                    else
                        GsonFactory.getCustomWhitDateFormat().toJson(obj)

                    params.append(arg).append(";")
                }
                /**
                 * 防止json字符串转换失败会导致字符越界
                 */
                if (params.isNotEmpty()) {
                    params.deleteCharAt(params.length - 1)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            logger.error(getErrStack(e))
            try {
                params = StringBuffer(String.format(ErrTemplate, e.message))
            } catch (e2: Exception) {
                logger.error(getErrStack(e))
            }

        }

        return params.toString()
    }

    internal fun getErrStack(e: Throwable): String {
        val stringBuffer = StringBuffer()
        for (elem in e.stackTrace) {
            stringBuffer.append(elem).append("\r\n")
        }

        return stringBuffer.toString()
    }


}