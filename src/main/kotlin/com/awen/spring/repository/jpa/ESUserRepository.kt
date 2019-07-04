package com.awen.spring.repository.jpa

import com.awen.spring.repository.entity.ESUser
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository


interface ESUserRepository: ElasticsearchRepository<ESUser, String> {



}