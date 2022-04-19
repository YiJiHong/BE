package com.example.be.entity

import com.example.be.dto.BoardDto
import com.example.be.dto.ContentDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
class Board (
    @Id
    val id: String,
    val userEmail: String,
    val nickName: String,
    val subtitle: String,
    val titleImage: String,
    val likes: Int,
    val modDateTime: LocalDateTime,
    val contents: Content
        ){

    fun toDataModel(): BoardDto {
        return BoardDto(
            id = id,
            userEmail = userEmail,
            nickName = nickName,
            subtitle = subtitle,
            titleImage = titleImage,
            likes = likes,
            modDateTime = modDateTime,
            contents = ContentDto(
                id = contents.id,
                no = contents.no,
                title = contents.title,
                subTitle = contents.subTitle,
                content = contents.content
            )
        )
    }
}
