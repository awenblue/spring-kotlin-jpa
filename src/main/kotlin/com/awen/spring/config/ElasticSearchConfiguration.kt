package com.awen.spring.config

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

@Component
class ElasticSearchConfiguration: InitializingBean {

    init {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    override fun afterPropertiesSet() {
        println(System.getProperty("es.set.netty.runtime.available.processors"));
    }
}