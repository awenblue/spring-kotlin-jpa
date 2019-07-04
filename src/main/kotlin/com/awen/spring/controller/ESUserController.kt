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

import com.awen.spring.repository.entity.ESUser
import com.awen.spring.repository.jpa.ESUserRepository
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource

@RestController
class ESUserController {

    @Resource
    lateinit var esUserRepository: ESUserRepository

    @PostMapping("/add")
    fun saveUser(@RequestBody user: ESUser) {
        esUserRepository.save(user)
    }

    @DeleteMapping("/{id:.*}")
    fun removeUser(@PathVariable("id") id: String) {
        esUserRepository.deleteById(id)
    }

    @PostMapping("/list")
    fun saveUsers(@RequestBody users: List<ESUser>) {
        esUserRepository.saveAll(users)
    }

    @GetMapping("/all")
    fun getAll(): List<ESUser> {
        return esUserRepository.findAll().toList()
    }


}