package com.example.be.dto

import java.time.LocalDateTime

data class InsertBoardDto(
    val id: String,
    val userEmail: String,
    val nickName: String,
    val subtitle: String,
    val titleImage: String,
    val modDateTime: LocalDateTime,
    val content: ContentDto
) {
}
