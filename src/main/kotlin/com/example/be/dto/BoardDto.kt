package com.example.be.dto

import java.time.LocalDateTime

data class BoardDto(
    val id: String,
    val userEmail: String,
    val nickName: String,
    val subTitle: String,
    val titleImage: String?,
    val likes: Int,
    val modDateTime: LocalDateTime,
    val contents: List<ContentDto>,
    val comments: List<CommentDto>
) {
}
