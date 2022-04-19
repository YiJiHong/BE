package com.example.be.dto

import java.time.LocalDateTime

class CommentDto(
    val id: String,
    val userId: String,
    val nickName: String,
    val comment: String,
    val modDateTime: LocalDateTime
) {
}
