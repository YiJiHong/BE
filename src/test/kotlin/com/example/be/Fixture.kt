package com.example.be

import com.example.be.dto.*
import com.example.be.entity.Board
import com.example.be.entity.Comment
import com.example.be.entity.Content
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

        val content = Content(
            no = 0,
            title = "testTitle",
            subTitle = "testSubTitle",
            content = "testContent"
        )

        val comment = Comment(
            id = "testId",
            userId = "testId",
            nickName = "testNickName",
            comment = "testComment",
            modDateTime = LocalDateTime.now()
        )

        val board = Board(
            id = "test",
            userEmail = "testEmail",
            nickName = "test",
            subTitle = "testTitle",
            titleImage = "testImage",
            likes = 0,
            modDateTime = LocalDateTime.now(),
            contents = Collections.singletonList(content),
            comments = Collections.singletonList(comment)
        )

        val insertBoardDto = InsertBoardDto(
            userEmail = "testEmail",
            nickName = "test",
            subTitle = "testTitle",
            titleImage = "testImage",
            modDateTime = LocalDateTime.now(),
            contents = Collections.singletonList(contentDto)
        )

        val updateBoardDto = UpdateBoardDto(
            id = "testId",
            subTitle = "testTitle",
            titleImage = "testImage",
            modDateTime = LocalDateTime.now(),
            contents = Collections.singletonList(contentDto),
            comments = Collections.singletonList(commentDto)
        )
    }
}
