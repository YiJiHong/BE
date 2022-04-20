package com.example.be.entity

import com.example.be.dto.BoardDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime
import java.util.Collections

@Document
data class Board (
    @Id
    val id: String,
    val userEmail: String,
    val nickName: String,
    val subtitle: String,
    val titleImage: String,
    val likes: Int,
    @Field("modDatetime")
    val modDateTime: LocalDateTime,
    val contents: List<Content>,
    val comments: List<Comment>?
        ){

    fun toDataModel(): BoardDto {
        return BoardDto(
            id = id,
            userEmail = userEmail,
            nickName = nickName,
            subTitle = subtitle,
            titleImage = titleImage,
            likes = likes,
            modDateTime = modDateTime,
            contents = contents.map {
                content: Content ->  content.toDataModel()
            },
            comments = comments?.map { comment: Comment ->
                comment.toDataModel()
            } ?: Collections.emptyList()
        )
    }
}
