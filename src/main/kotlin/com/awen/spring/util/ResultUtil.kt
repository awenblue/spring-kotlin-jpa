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

package com.awen.spring.util

import com.awen.spring.common.constant.ErrorType
import com.awen.spring.common.dto.ResultDTO

object ResultUtil {

    private const val SUCCESS = 10000

    fun failed(): ResultDTO<Any> {

        return failed(ErrorType.ERROR_NO_DATA)
    }

    fun failed(errorType: ErrorType): ResultDTO<Any> {

        return ResultDTO(errorType.errorId, errorType.errorMessage)
    }

    fun success(): ResultDTO<Any> {

        return success(null)
    }

    fun <T> success(`object`: T?): ResultDTO<T> {

        return ResultDTO(SUCCESS, null, `object`)
    }



}