## Spring-Kotlin-jpa

    使用spring boot 2.0 + kotlin + jpa创建的基础项目，用于新项目的创建
    



## 目录简介

    common      请求参数和返回DTO 
    config      日志aop，swagger接口配置 
    controller  controller 
    repository  数据库配置，包含jpa
    service     逻辑service和数据service 
    util        工具



## 功能配置

    接口统一日志打印 ✔
    验证码生成  ✔
    memcached缓存配置  ✔
    登录token生成   ✖
    请求头token校验 ✖
    redis配置  ✖
    
## 运行环境

    安装memcached，连接地址在cache-client.properties配置，多个用逗号,分隔

## License

    Copyright 2018 The awen_blue Authors

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.