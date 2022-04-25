package com.example.be.dto

import com.example.be.entity.Content

data class ContentDto(
    val no: Int,
    val title: String?,
    val subTitle: String?,
    val content: String?
) {
    fun toEntity(): Content {
        return Content(
            no = this.no,
            title = this.title,
            subTitle = this.subTitle,
            content = this.content
        )
    }
}
