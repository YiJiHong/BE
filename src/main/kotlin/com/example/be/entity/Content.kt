package com.example.be.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Content (
    @Id
    val id: String,
    val no: Int,
    val title: String,
    val subTitle: String,
    val content: String
){
}
