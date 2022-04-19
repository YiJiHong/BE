package com.example.be.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
class Board (
    @Id
    val id: String,
    val userEmail: String,
    val nickName: String,
    val subtitle: String,
    val titleImage: String,
    val likes: Int,
    val modDateTime: LocalDateTime,
    val contents: Content
        ){
}

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
