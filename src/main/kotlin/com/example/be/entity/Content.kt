package com.example.be.entity

import com.example.be.dto.ContentDto

data class Content (
    val no: Int,
    val title: String?,
    val subTitle: String?,
    val content: String?
){
    fun toDataModel(): ContentDto {
        return ContentDto(
            no = no,
            title = title,
            subTitle = subTitle,
            content = content
        )
    }
}
