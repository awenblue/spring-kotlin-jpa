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

package com.awen.spring.service

import com.awen.spring.common.constant.ErrorType
import com.awen.spring.common.dto.ResultDTO
import com.awen.spring.common.dto.user.UserDTO
import com.awen.spring.common.param.user.UserLoginParam
import com.awen.spring.service.data.UserDataService
import com.awen.spring.util.ResultUtil
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class UserLogicService {

    @Resource
    private lateinit var userDataService: UserDataService


    fun userLogin(param: UserLoginParam): ResultDTO<Any> {
        if (param.account == null || param.password == null) {
            return ResultUtil.failed(ErrorType.ERROR_USER_LOGIN)
        }

        val userEntity = userDataService.getByAccount(param.account!!, param.password!!)
                ?: return ResultUtil.failed(ErrorType.ERROR_USER_LOGIN)

        return ResultUtil.success(UserDTO(
                userEntity.id, userEntity.account
        ))
    }


}