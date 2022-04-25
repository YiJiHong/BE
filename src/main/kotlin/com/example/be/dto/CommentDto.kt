package com.example.be.dto

import com.example.be.entity.Comment
import java.time.LocalDateTime

data class CommentDto(
    val id: String,
    val userId: String,
    val nickName: String,
    val comment: String,
    val modDateTime: LocalDateTime
) {
    fun toEntity(): Comment{
        return Comment(
            id = this.id,
            userId = this.userId,
            nickName = this.nickName,
            comment = this.comment,
            modDateTime = this.modDateTime
        )
    }
}
