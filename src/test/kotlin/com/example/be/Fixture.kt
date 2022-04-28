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

        val boardWithoutComment = Board(
            id = "test",
            userEmail = "testEmail",
            nickName = "test",
            subTitle = "testTitle",
            titleImage = "testImage",
            likes = 0,
            modDateTime = LocalDateTime.now(),
            contents = Collections.singletonList(content),
            comments = null
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

        val userDto = UserDto(
            id = "testId",
            email = "testEmail@naver.com",
            name = "testName",
            nickName = "test nickName",
            intro = "test",
            profileImage = "/src/image",
            scraps = Collections.emptyList(),
            likes = Collections.singletonList(boardDto.id),
            followers = Collections.singletonList("testId2"),
            followings = Collections.emptyList(),
            tags = Collections.singletonList("여행")
        )

        val userRegisterDto = UserRegisterDto(
            email = "test@naver.com",
            password = "test123"
        )
    }
}
