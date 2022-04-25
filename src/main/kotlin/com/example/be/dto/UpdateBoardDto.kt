package com.example.be.dto

import java.time.LocalDateTime

data class UpdateBoardDto(
    val id: String,
    val subTitle: String,
    val titleImage: String,
    val modDateTime: LocalDateTime,
    val contents: List<ContentDto>,
    val comments: List<CommentDto>
) {
}
