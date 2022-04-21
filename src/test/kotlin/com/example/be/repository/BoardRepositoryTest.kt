package com.example.be.repository

import com.example.be.dto.BoardDto
import com.example.be.dto.CommentDto
import com.example.be.dto.ContentDto
import com.example.be.entity.Board
import com.example.be.entity.Comment
import com.example.be.entity.Content
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(SpringExtension::class)
@DataMongoTest
class BoardRepositoryTest {

    @Autowired
    lateinit var boardRepository: BoardRepository

    private val contentDto = ContentDto(
        no = 0,
        title = "testTitle",
        subTitle = "testSubTitle",
        content = "testContent"
    )

    private val commentDto = CommentDto(
        id = "testId",
        userId = "testId",
        nickName = "testNickName",
        comment = "testComment",
        modDateTime = LocalDateTime.now()
    )

    private val boardDto = BoardDto(
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

    @Test
    fun test00() {
        val pageable = PageRequest.of(0, 10)
        val board: Page<Board> = boardRepository.findAllByUserEmail(pageable, "nsh_823@naver.com")

        board.forEach{
            board ->
            println("board = ${board}")
            println("for explain")
        }
    }

    @Test
    @DisplayName("findAllByUserEmail에서 존재하지 않는 email로 검색을 하면 비어있는 content를 page에 감싸서 반환한다.")
    fun test01() {
        val pageable = PageRequest.of(0, 10)
        val board: Page<Board> = boardRepository.findAllByUserEmail(pageable, "none")

        Assertions.assertTrue(board.content.isEmpty())
    }

    @Test
    @DisplayName("delete test")
    fun test02() {
        boardRepository.deleteById("-1");


    }


    private fun save(): Board {
        val board = Board(
            id = boardDto.id,
            userEmail = boardDto.userEmail,
            nickName = boardDto.nickName,
            subtitle = boardDto.subTitle,
            titleImage = boardDto.titleImage,
            likes = boardDto.likes,
            modDateTime = boardDto.modDateTime,
            contents = Collections.singletonList(
                Content(
                    no = contentDto.no,
                    title = contentDto.title,
                    subTitle = contentDto.subTitle,
                    content = contentDto.content
                )
            ),
            comments = Collections.singletonList(
                Comment(
                    commentDto.id,
                    commentDto.userId,
                    commentDto.nickName,
                    commentDto.comment,
                    commentDto.modDateTime,
                )
            )
        )
        return boardRepository.save(board)
    }

}
