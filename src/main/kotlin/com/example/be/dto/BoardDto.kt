package com.example.be.dto

import java.time.LocalDateTime

class BoardDto(
    val id: String,
    val userEmail: String,
    val nickName: String,
    val subtitle: String,
    val titleImage: String,
    val likes: Int,
    val modDateTime: LocalDateTime,
    val contents: List<ContentDto>,
    val comments: List<CommentDto>
) {
}
