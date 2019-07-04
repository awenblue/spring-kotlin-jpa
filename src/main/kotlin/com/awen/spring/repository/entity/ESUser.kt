package com.awen.spring.repository.entity

import org.springframework.data.elasticsearch.annotations.Document
import java.util.*


@Document(indexName = "user", type = "test")
data class ESUser (
        var id: String,
        var name: String,
        var creationDate: Date = Date()
) {

    constructor(): this("", "")

}