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

package com.awen.spring.common

object RestUrl {

    private const val APP_MODULE = "/app"
    private const val VERSION = "/v1"
    private const val APP_BASE_URL = APP_MODULE + VERSION

    const val HOME = "$APP_BASE_URL/home"


    private const val USER = "$APP_BASE_URL/user"
    const val userLOGIN = "$USER/login"

    private const val COMMON = "$APP_BASE_URL/common"
    const val sendSms = "$COMMON/sendSms"

}