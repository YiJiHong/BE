package com.example.be.controller

import com.example.be.SpringMockMvcTestSupport
import com.example.be.dto.BoardDto
import com.example.be.dto.CommentDto
import com.example.be.dto.ContentDto
import com.example.be.dto.InsertBoardDto
import com.example.be.service.BoardService
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime
import java.util.Collections

@WebMvcTest(controllers = [BoardController::class])
internal class BoardControllerTest : SpringMockMvcTestSupport() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var service: BoardService

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

    @Nested
    @DisplayName("Get 방식으로 통신할 때")
    inner class GetMappingControllerTest {

        @Test
        @DisplayName("uri에 userEmail이 path로 들어오는 경우, userEmail에 대응되는 board들을 감싼 page와 상태 200을 반환한다.")
        fun test00() {
            // given
            val userEmail = boardDto.userEmail
            val inputUri = "/board/${userEmail}"


            val pageImpl = PageImpl(listOf(boardDto))

            // when
            Mockito.`when`(service.getAllBoard(Mockito.anyString())).thenReturn(pageImpl)
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(inputUri)
            )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
        }
    }

    @Nested
    @DisplayName("POST 방식으로 통신할 때")
    inner class PostMappingControllerTest {

        @Test
        @DisplayName("http의 body를 통해 insertBoardDto를 받는 경우, Db에 저장을 성공하면 200을 반환한다.")
        fun test00() {
            // given
            val inputUri = "/board"
            val insertBoardDto = InsertBoardDto(
                id = boardDto.id,
                userEmail = boardDto.userEmail,
                nickName = boardDto.nickName,
                subtitle = boardDto.subTitle,
                titleImage = boardDto.titleImage,
                modDateTime = LocalDateTime.now(),
                content = contentDto
            )

            // when
            Mockito.`when`(service.insertBoard(any())).thenReturn(true)
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(inputUri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(insertBoardDto))
            )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
        }
    }

    @Nested
    @DisplayName("PUT 방식으로 통신할 때")
    inner class PutMappingControllerTest {

        @Test
        @DisplayName("http의 body를 통해 insertBoardDto를 받는 경우, Db에 저장을 성공하면 200을 반환한다.")
        fun test00() {
            // given
            val inputUri = "/board"
            val insertBoardDto = InsertBoardDto(
                id = boardDto.id,
                userEmail = boardDto.userEmail,
                nickName = boardDto.nickName,
                subtitle = boardDto.subTitle,
                titleImage = boardDto.titleImage,
                modDateTime = LocalDateTime.now(),
                content = contentDto
            )

            // when
            Mockito.`when`(service.insertBoard(any())).thenReturn(true)
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(inputUri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(insertBoardDto))
            )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
        }
    }

    @Nested
    @DisplayName("DELETE 방식으로 통신할 때")
    inner class DeleteMappingControllerTest {

        @Test
        @DisplayName("uri에 boardId가 path로 들어오는 경우, userEmail에 대응되는 board를 삭제하고 성공하면 true와 상태 200을 반환한다.")
        fun test00() {
            // given
            val boardId = "boardId"
            val inputUri = "/board/${boardId}"

            // when
            Mockito.`when`(service.deleteBoard(Mockito.anyString())).thenReturn(true)
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(inputUri)
            )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
        }
    }
}
