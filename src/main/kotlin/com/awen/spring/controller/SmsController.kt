package com.awen.spring.controller

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


        val validateCode = RandomUtil.generateValidateCode()

        taskService.run(DemoSmsTaskService(areaCode, phoneNumber, validateCode, smsEnum))

        return ResultUtil.success()
    }
}