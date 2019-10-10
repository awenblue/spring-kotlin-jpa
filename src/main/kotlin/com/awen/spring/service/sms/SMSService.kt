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

package com.awen.spring.service.sms

import com.awen.spring.common.constant.SMSType

abstract class SMSService(
        var phoneNumber: String,
        var validationCode: String,
        var areaCode: String,
        var smsType: SMSType
): Runnable {

    abstract fun sms(): Boolean

    override fun run() {
        sms()
    }

    override fun toString(): String {
        return "SMSService(phoneNumber='$phoneNumber', validationCode='$validationCode', areaCode='$areaCode', smsType=$smsType)"
    }


}