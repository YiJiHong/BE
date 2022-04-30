package com.example.be.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document
class User(
    @Id
    val id: String?,
    val email: String,
    val name: String,
    @Field("nickname")
    val nickName: String,
    val intro: String?,
    val profileImage: String?,
    val scraps: List<String>,
    val likes: List<String>,
    val followers: List<String>,
    val followings: List<String>,
    val tags: List<String>
) {


}
