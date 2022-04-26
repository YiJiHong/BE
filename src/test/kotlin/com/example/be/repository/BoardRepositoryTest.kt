package com.example.be.repository

import com.example.be.Fixture
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
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(SpringExtension::class)
@DataMongoTest
@ActiveProfiles("test")
class BoardRepositoryTest {

    @Autowired
    lateinit var boardRepository: BoardRepository

    @Test
    fun test00() {
        val pageable = PageRequest.of(0, 10)
        val saveBoard = saveNullId()
        val board: Page<Board> = boardRepository.findAllByUserEmail(pageable, saveBoard.userEmail)

        board.content.forEach {
            Assertions.assertEquals(saveBoard.userEmail, it.userEmail)
        }
        delete(saveBoard.id!!)
    }

    @Test
    @DisplayName("findAllByUserEmail에서 존재하지 않는 email로 검색을 하면 비어있는 content를 page에 감싸서 반환한다.")
    fun test01() {
        val pageable = PageRequest.of(0, 10)
        val board: Page<Board> = boardRepository.findAllByUserEmail(pageable, "none")

        Assertions.assertTrue(board.content.isEmpty())
    }

    @Test
    @DisplayName("findBoardByBoardId Success")
    fun test02() {
        val saveBoard = saveNullId()
        val board: Board? = boardRepository.findBoardById(saveBoard.id!!)

        // LocalDateTime type의 경우
        // ex. 2022-04-26T17:11:12.153230에서
        // findBoardById로 찾아오면 2022-04-26T17:11:12.153230
        // save()후 반환 받은 entity에서는 2022-04-26T17:11:12.153로 나타나 값이 조금 다름.
        org.assertj.core.api.Assertions.assertThat(board).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime::class.java).isEqualTo(saveBoard)

        delete(saveBoard.id!!)
    }

    @Test
    @DisplayName("findBoardByBoardId Fail")
    fun test03() {
        val board: Board? = boardRepository.findBoardById("None")

        Assertions.assertNull(board)
    }

    @Test
    @DisplayName("delete test Success - 1L을 반환")
    fun test04() {
        val saveBoard = saveNullId()
        val deleteBoardById1 = boardRepository.deleteBoardById(saveBoard.id!!)

        Assertions.assertEquals(1L, deleteBoardById1)
    }

    @Test
    @DisplayName("delete test Fail - 0L을 반환")
    fun test05() {
        val deleteBoardById1 = boardRepository.deleteBoardById("NONE")

        Assertions.assertEquals(0L, deleteBoardById1)
    }


    @Test
    @DisplayName("update test")
    fun test06() {
        val board = saveNullId()

        val updateNickName = "update"

        val updateBoard = Board(
            id = board.id,
            userEmail = board.userEmail,
            nickName = updateNickName,
            subTitle = board.subTitle,
            titleImage = board.titleImage,
            likes = board.likes,
            modDateTime = board.modDateTime,
            contents = Collections.singletonList(
                Content(
                    no = board.contents[0].no,
                    title = board.contents[0].title,
                    subTitle = board.contents[0].subTitle,
                    content = board.contents[0].content
                )
            ),
            comments = Collections.singletonList(
                Comment(
                    board.comments!![0].id,
                    board.comments!![0].userId,
                    board.comments!![0].nickName,
                    board.comments!![0].comment,
                    board.comments!![0].modDateTime,
                )
            )
        )
        val update = boardRepository.save(updateBoard)

        org.assertj.core.api.Assertions.assertThat(update).usingRecursiveComparison().ignoringFields("nickName").isEqualTo(board)

        delete(update.id!!)
    }

    private fun delete(boardId: String): Long {
        return boardRepository.deleteBoardById(boardId)
    }

    private fun saveNullId(): Board {
        val board = Board(
            id = null,
            userEmail = Fixture.boardDto.userEmail,
            nickName = Fixture.boardDto.nickName,
            subTitle = Fixture.boardDto.subTitle,
            titleImage = Fixture.boardDto.titleImage,
            likes = Fixture.boardDto.likes,
            modDateTime = Fixture.boardDto.modDateTime,
            contents = Collections.singletonList(
                Content(
                    no = Fixture.contentDto.no,
                    title = Fixture.contentDto.title,
                    subTitle = Fixture.contentDto.subTitle,
                    content = Fixture.contentDto.content
                )
            ),
            comments = Collections.singletonList(
                Comment(
                    Fixture.commentDto.id,
                    Fixture.commentDto.userId,
                    Fixture.commentDto.nickName,
                    Fixture.commentDto.comment,
                    Fixture.commentDto.modDateTime,
                )
            )
        )
        return boardRepository.save(board)
    }
}
