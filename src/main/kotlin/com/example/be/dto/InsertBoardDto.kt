package com.example.be.dto

import com.example.be.entity.Board
import java.time.LocalDateTime

data class InsertBoardDto(
    val userEmail: String,
    val nickName: String,
    val subTitle: String,
    val titleImage: String?,
    val modDateTime: LocalDateTime,
    val contents: List<ContentDto>
) {
    fun toEntity(): Board {
        return Board(
            id = null,
            userEmail = this.userEmail,
            nickName = this.nickName,
            subTitle = this.subTitle,
            titleImage = this.titleImage,
            likes = 0,
            modDateTime = this.modDateTime,
            contents = this.contents.map { contentDto -> contentDto.toEntity() },
            comments = null
        )
    }
}
