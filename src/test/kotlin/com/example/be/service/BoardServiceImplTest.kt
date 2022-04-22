package com.example.be.service

import com.example.be.Fixture
import com.example.be.dto.BoardDto
import com.example.be.entity.Board
import com.example.be.repository.BoardRepository
import com.nhaarman.mockitokotlin2.any
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class BoardServiceImplTest {

    @InjectMocks
    lateinit var service: BoardServiceImpl

    @Mock
    lateinit var repository: BoardRepository

    @Nested
    @DisplayName("getAllBoard Test")
    inner class TestGetAllBoard {

        @Test
        @DisplayName("DB에 userEmail에 대응되는 board가 있다면, board를 BoardDto로 변환하고 Page에 감싸서 반환한다.")
        fun test00() {
            // given
            val pageRequest = PageRequest.of(0, 10)
            val userEmail = Fixture.boardDto.userEmail
            val list: PageImpl<Board> = PageImpl(listOf(Fixture.board))

            // when
            Mockito.`when`(repository.findAllByUserEmail(any(), Mockito.anyString())).thenReturn(list)

            // then
            val boardDtoPage: Page<BoardDto> = service.getAllBoard(pageRequest, userEmail)
            boardDtoPage.forEach{
                boardDto -> assertEquals(Fixture.boardDto.userEmail, boardDto.userEmail)
            }
        }

        @Test
        @DisplayName("DB에 userEmail에 대응되는 board가 없다면, content가 empty인 Page를 반환한다.")
        fun test01() {
            // given
            val pageRequest = PageRequest.of(0, 10)
            val userEmail = "Error"
            val emptyList = Page.empty<Board>()

            // when
            Mockito.`when`(repository.findAllByUserEmail(any(), Mockito.anyString())).thenReturn(emptyList)

            // then
            val boardDtoPage: Page<BoardDto> = service.getAllBoard(pageRequest, userEmail)
            assertTrue(boardDtoPage.isEmpty)
        }
    }

}
