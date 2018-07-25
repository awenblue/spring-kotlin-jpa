package com.awen.spring.service.sms

import com.awen.spring.common.constant.SMSType
import org.slf4j.LoggerFactory

class DemoSmsTaskService(
        areaCode: String ,
        phoneNumber: String,
        validationCode: String,
        smsType: SMSType
): SMSService(phoneNumber, validationCode, areaCode, smsType) {

    private val logger = LoggerFactory.getLogger(DemoSmsTaskService::class.java)

    override fun sms(): Boolean {



        logger.info(this.toString())












        return true
    }

    override fun run() {
        sms()
    }

}