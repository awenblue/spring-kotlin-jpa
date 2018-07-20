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

package com.awen.spring.controller

import com.awen.spring.common.RestUrl
import com.awen.spring.common.dto.ResultDTO
import com.awen.spring.common.dto.user.UserDTO
import com.awen.spring.common.param.user.UserLoginParam
import com.awen.spring.service.UserLogicService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
class UserController {

    @Resource
    private lateinit var userLogicService: UserLogicService

    @PostMapping(RestUrl.USER_LOGIN, produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
    @ResponseBody
    fun userLogin(@RequestBody param: UserLoginParam): ResultDTO<UserDTO> = userLogicService.userLogin(param) as ResultDTO<UserDTO>





}