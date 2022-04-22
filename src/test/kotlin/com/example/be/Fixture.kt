package com.example.be

import com.example.be.dto.BoardDto
import com.example.be.dto.CommentDto
import com.example.be.dto.ContentDto
import java.time.LocalDateTime
import java.util.*

class Fixture {

    companion object {
        val contentDto = ContentDto(
            no = 0,
            title = "testTitle",
            subTitle = "testSubTitle",
            content = "testContent"
        )

        val commentDto = CommentDto(
            id = "testId",
            userId = "testId",
            nickName = "testNickName",
            comment = "testComment",
            modDateTime = LocalDateTime.now()
        )

        val boardDto = BoardDto(
            id = "test",
            userEmail = "testEmail",
            nickName = "test",
            subTitle = "testTitle",
            titleImage = "testImage",
            likes = 0,
            modDateTime = LocalDateTime.now(),
            contents = Collections.singletonList(contentDto),
            comments = Collections.singletonList(commentDto)
        )
    }
}
