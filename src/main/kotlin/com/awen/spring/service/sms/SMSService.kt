package com.awen.spring.service.sms

import com.awen.spring.common.constant.SMSType

abstract class SMSService(
        var phoneNumber: String,
        var validationCode: String,
        var areaCode: String,
        var smsType: SMSType
): Runnable {

    abstract fun sms(): Boolean

}