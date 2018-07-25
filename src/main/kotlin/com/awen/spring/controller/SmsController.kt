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

import com.awen.spring.cache.OcsKeyConfig
import com.awen.spring.cache.OcsKeyManager
import com.awen.spring.common.RestUrl
import com.awen.spring.common.constant.SMSType
import com.awen.spring.common.dto.ResultDTO
import com.awen.spring.service.TaskService
import com.awen.spring.service.sms.DemoSmsTaskService
import com.awen.spring.util.RandomUtil
import com.awen.spring.util.ResultUtil
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
class SmsController {

    @Resource
    private lateinit var taskService: TaskService

    @Resource
    private lateinit var ocsKeyManager: OcsKeyManager

    @RequestMapping(RestUrl.sendSms)
    fun sendSms(
            @RequestParam("areaCode") areaCode: String ,
            @RequestParam("phoneNumber") phoneNumber: String,
            @RequestParam("smsType") smsType: Int
    ): ResultDTO<Any> {
        val smsEnum = if (smsType == 1) {
            SMSType.SMS_TYPE_USER_REGISTER
        } else {
            SMSType.SMS_TYPE_USER_UPDATE_PASSWORD
        }

        ocsKeyManager["a", OcsKeyConfig.TEN_MIN] = "a"
        println(ocsKeyManager["a"])

        val validateCode = RandomUtil.generateValidateCode()

        taskService.run(DemoSmsTaskService(areaCode, phoneNumber, validateCode, smsEnum))

        return ResultUtil.success()
    }
}