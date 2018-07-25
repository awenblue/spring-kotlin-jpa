package com.awen.spring.service.sms

import com.awen.spring.common.constant.SMSType

class DemoSmsTaskService(
        areaCode: String ,
        phoneNumber: String,
        validationCode: String,
        smsType: SMSType
): SMSService(phoneNumber, validationCode, areaCode, smsType) {

    
    override fun sms(): Boolean {
















        return true
    }

    override fun run() {
        sms()
    }

}