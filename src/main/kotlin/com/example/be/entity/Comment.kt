package com.example.be.entity

import com.example.be.dto.CommentDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(value = "comments")
data class Comment (
    @Id
    val id: String,
    val userId: String,
    val nickName: String,
    val comment: String,
    val modDateTime: LocalDateTime
        ){
    fun toDataModel(): CommentDto {
        return CommentDto(
            id = id,
            userId = userId,
            nickName = nickName,
            comment = comment,
            modDateTime = modDateTime
        )
    }
}
