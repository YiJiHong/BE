package com.example.be.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class UserRegisterInfo (
    @Id
    val id: String,
    val password: String
    ){
}
