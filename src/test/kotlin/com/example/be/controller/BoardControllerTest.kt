package com.example.be.controller

import com.example.be.SpringMockMvcTestSupport
import com.example.be.dto.BoardDto
import com.example.be.dto.CommentDto
import com.example.be.dto.ContentDto
import com.example.be.service.BoardService
import com.fasterxml.jackson.databind.ObjectMapper
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

    @Nested
    @DisplayName("Get 방식으로 통신할 때")
    inner class GetMappingControllerTest {

        @Test
        fun test00() {
            // given
            val userEmail = "sehonge1602"
            val inputUri = "/board/${userEmail}"
            val boardDto = BoardDto(
                id = "test",
                userEmail = userEmail,
                nickName = "test",
                subtitle = "testTitle",
                titleImage = "testImage",
                likes = 0,
                modDateTime = LocalDateTime.now(),
                contents = Collections.singletonList(
                    ContentDto(0, null, null, null)
                ),
                comments = Collections.singletonList(
                    CommentDto("testId", "pong", "test", "test", LocalDateTime.now())
                )
            )
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

}