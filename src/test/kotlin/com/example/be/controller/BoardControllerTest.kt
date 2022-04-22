package com.example.be.controller

import com.example.be.Fixture
import com.example.be.SpringMockMvcTestSupport
import com.example.be.dto.InsertBoardDto
import com.example.be.dto.UpdateBoardDto
import com.example.be.exception.NoneBoardException
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
import java.util.*

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
        @DisplayName("uri에 userEmail이 path로 들어오는 경우, userEmail에 대응되는 board들을 감싼 page와 상태 200을 반환한다.")
        fun test00() {
            // given
            val userEmail = Fixture.boardDto.userEmail
            val inputUri = "/board/${userEmail}"


            val pageImpl = PageImpl(listOf(Fixture.boardDto))

            // when
            Mockito.`when`(service.getAllBoard(any(), Mockito.anyString())).thenReturn(pageImpl)
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(inputUri)
            )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
        }

        @Test
        @DisplayName("uri에 boardId가 path로 들어오는 경우, BoardId에 대응되는 boardDto와 상태 200을 반환한다.")
        fun test01() {
            // given
            val boardId = Fixture.boardDto.id
            val inputUri = "/board"

            // when
            Mockito.`when`(service.getBoard(Mockito.anyString())).thenReturn(Fixture.boardDto)
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(inputUri)
                    .param("boardId", boardId)
            )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
        }

        @Test
        @DisplayName("uri에 boardId가 path로 들어오는 경우, BoardId에 대응되는 board가 없다면 404을 반환한다.")
        fun test02() {
            // given
            val boardId = Fixture.boardDto.id
            val inputUri = "/board"

            // when
            Mockito.`when`(service.getBoard(Mockito.anyString())).thenThrow(NoneBoardException("test message"))
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(inputUri)
                    .param("boardId", boardId)
            )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound)
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
                id = Fixture.boardDto.id,
                userEmail = Fixture.boardDto.userEmail,
                nickName = Fixture.boardDto.nickName,
                subTitle = Fixture.boardDto.subTitle,
                titleImage = Fixture.boardDto.titleImage,
                modDateTime = LocalDateTime.now(),
                contents = Collections.singletonList(Fixture.contentDto)
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

        @Test
        @DisplayName("http의 body를 통해 잘못된 insertBoardDto를 받는 경우, HttpMessageNotReadableException과 BAD_REQUEST(400)를 반환한다.")
        fun test01() {
            // given
            val inputUri = "/board"
            val inputWithoutUserEmail = "{ " +
                    "\"id\" : \"1234\"," +
                    "\"nickName\" : \"test\"," +
                    "\"subTitle\" : \"test\"," +
                    "\"titleImage\" : \"test\"," +
                    "\"modDateTime\" : \"2022-04-11T12:00:00\"," +
                    "\"contents\" : [" +
                        "{ "+
                            "\"no\" : \"0\"," +
                            "\"title\" : \"test\"," +
                            "\"subtitle\" : \"test\"," +
                            "\"content\" : \"test\"" +
                        "}" +
                    "]" +
                "}"

            // when
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(inputUri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(inputWithoutUserEmail)
            )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
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
            val updateBoardDto = UpdateBoardDto(
                id = Fixture.boardDto.id,
                userEmail = Fixture.boardDto.userEmail,
                nickName = Fixture.boardDto.nickName,
                subTitle = Fixture.boardDto.subTitle,
                titleImage = Fixture.boardDto.titleImage,
                modDateTime = LocalDateTime.now(),
                contents = Collections.singletonList(Fixture.contentDto),
                comments = Collections.singletonList(Fixture.commentDto)
            )

            // when
            Mockito.`when`(service.insertBoard(any())).thenReturn(true)
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(inputUri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(updateBoardDto))
            )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
        }

        @Test
        @DisplayName("http의 body를 통해 잘못된 updateBoardDto를 받는 경우, HttpMessageNotReadableException과 BAD_REQUEST(400)를 반환한다.")
        fun test01() {
            // given
            val inputUri = "/board"
            val inputWithoutComments = "{ " +
                        "\"id\" : \"1234\"," +
                        "\"userEmail\" : \"1234\"," +
                        "\"nickName\" : \"test\"," +
                        "\"subTitle\" : \"test\"," +
                        "\"titleImage\" : \"test\"," +
                        "\"modDateTime\" : \"2022-04-11T12:00:00\"," +
                        "\"contents\" : [" +
                            "{ "+
                                "\"no\" : \"0\"," +
                                "\"title\" : \"test\"," +
                                "\"subtitle\" : \"test\"," +
                                "\"content\" : \"test\"" +
                            "}" +
                        "]" +
                    "}"

            // when
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(inputUri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(inputWithoutComments)
            )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
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

        @Test
        @DisplayName("uri에 boardId가 path로 들어오는 경우, userEmail에 대응되는 board를 삭제가 실패하면 false와 상태 404을 반환한다.")
        fun test01() {
            // given
            val boardId = "boardId"
            val inputUri = "/board/${boardId}"

            // when
            Mockito.`when`(service.deleteBoard(Mockito.anyString())).thenReturn(false)
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(inputUri)
            )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
        }
    }
}
