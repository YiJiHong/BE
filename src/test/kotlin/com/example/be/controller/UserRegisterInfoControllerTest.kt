package com.example.be.controller

import com.example.be.Fixture
import com.example.be.SpringMockMvcTestSupport
import com.example.be.exception.NoneUserException
import com.example.be.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [UserController::class])
internal class UserRegisterInfoControllerTest : SpringMockMvcTestSupport() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var userService: UserService

    @Nested
    @DisplayName("Get 방식으로 요청")
    inner class GetMappingTest {

        @Nested
        @DisplayName("endpoint \"/user\"를 통해 header에 userId를 가지고 요청이 들어온경우")
        inner class FindUserRegisterInfoTest {
            @Test
            @DisplayName("id에 대응되는 user가 있다면, 대응되는 userDto와 200을 반환한다.")
            fun test00() {
                // given
                val inputUri: String = "/user"
                val inputId = Fixture.userDto.id

                // when
                Mockito.`when`(userService.getUserProfile(inputId)).thenReturn(Fixture.userDto)
                val actions = mockMvc.perform(
                    MockMvcRequestBuilders.get(inputUri)
                        .param("userId", inputId)
                )

                // then
                actions
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
            }

            @Test
            @DisplayName("id에 대응되는 user가 없다면, 404를 반환한다.")
            fun test01() {
                // given
                val inputUri = "/user"
                val inputId = "None"

                // when
                Mockito.`when`(userService.getUserProfile(inputId)).thenThrow(NoneUserException("No User. id = ${inputId}"))
                val actions = mockMvc.perform(
                    MockMvcRequestBuilders.get(inputUri)
                        .param("userId", inputId)
                )

                // then
                actions
                    .andExpect(MockMvcResultMatchers.status().isNotFound)
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
            }
        }

        @Nested
        @DisplayName("endpoint \"/user/login\"를 통해 header에 userId와 password를 가지고 요청이 들어온경우")
        inner class LoginTest {
            @Test
            @DisplayName("id, password에 대응되는 user가 있다면, true와 200을 반환한다.")
            fun test00() {
                // given
                val inputUri = "/user/login"
                val email = Fixture.userRegisterDto.email
                val password = Fixture.userRegisterDto.password

                // when
                Mockito.`when`(userService.login(any())).thenReturn(true)
                val actions = mockMvc.perform(
                    MockMvcRequestBuilders.get(inputUri)
                        .param("email", email)
                        .param("password", password)
                )

                // then
                actions
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
            }

            @Test
            @DisplayName("id, password에 대응되는 user가 없다면, false와 404를 반환한다.")
            fun test01() {
                // given
                val inputUri = "/user/login"
                val userId = Fixture.userRegisterDto.email
                val password = "wrong password"

                // when
                Mockito.`when`(userService.login(any())).thenReturn(false)
                val actions = mockMvc.perform(
                    MockMvcRequestBuilders.get(inputUri)
                        .param("email", userId)
                        .param("password", password)
                )

                // then
                actions
                    .andExpect(MockMvcResultMatchers.status().isNotFound)
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    @Nested
    @DisplayName("Post 방식으로 요청")
    inner class PostMappingTest {

        @Nested
        @DisplayName("endpoint \"/user\"를 통해 body에 email, password를 가지고 요청이 들어온경우")
        inner class RegisterUserRegisterInfoTest {
            @Test
            @DisplayName("등록에 성공하면, true와 200을 반환한다.")
            fun test00() {
                // given
                val inputUri: String = "/user"

                // when
                Mockito.`when`(userService.register(Fixture.userRegisterDto)).thenReturn(true)
                val actions = mockMvc.perform(
                    MockMvcRequestBuilders.post(inputUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(Fixture.userRegisterDto))
                )

                // then
                actions
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
            }

            @Test
            @DisplayName("등록에 실패하면, false와 500를 반환한다.")
            fun test01() {
                // given
                val inputUri = "/user"

                // when
                Mockito.`when`(userService.register(Fixture.userRegisterDto)).thenReturn(false)
                val actions = mockMvc.perform(
                    MockMvcRequestBuilders.post(inputUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(Fixture.userRegisterDto))
                )

                // then
                actions
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError)
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    @Nested
    @DisplayName("Put 방식으로 요청")
    inner class PutMappingTest {

        @Nested
        @DisplayName("endpoint \"/user\"를 통해 요청이 들어온경우")
        inner class RegisterUserRegisterInfoTest {
            @Test
            @DisplayName("유저 프로필 업데이트에 성공하면, true와 200을 반환한다.")
            fun test00() {
                // given
                val inputUri: String = "/user"
                val updateUserDto = Fixture.updateUserDto

                // when
                Mockito.`when`(userService.updateUserProfile(updateUserDto)).thenReturn(true)
                val actions = mockMvc.perform(
                    MockMvcRequestBuilders.put(inputUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateUserDto))
                )

                // then
                actions
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
            }

            @Test
            @DisplayName("유저 프로필 업데이트에 실패하면, false와 500를 반환한다.")
            fun test01() {
                // given
                val inputUri: String = "/user"
                val updateUserDto = Fixture.updateUserDto

                // when
                Mockito.`when`(userService.updateUserProfile(updateUserDto)).thenReturn(false)
                val actions = mockMvc.perform(
                    MockMvcRequestBuilders.put(inputUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateUserDto))
                )

                // then
                actions
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError)
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    @Nested
    @DisplayName("Delete 방식으로 요청")
    inner class DeleteMappingTest {

        @Nested
        @DisplayName("endpoint \"/user\"를 통해 요청이 들어온경우")
        inner class RegisterUserRegisterInfoTest {
            @Test
            @DisplayName("유저 삭제에 성공하면, true와 200을 반환한다.")
            fun test00() {
                // given
                val inputUri: String = "/user"
                val userId = Fixture.userDto.id

                // when
                Mockito.`when`(userService.deleteUser(userId)).thenReturn(true)
                val actions = mockMvc.perform(
                    MockMvcRequestBuilders.delete(inputUri)
                        .param("userId", userId)
                )

                // then
                actions
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
            }

            @Test
            @DisplayName("유저 삭제에 실패하면, false와 500를 반환한다.")
            fun test01() {
                // given
                val inputUri: String = "/user"
                val userId = Fixture.userDto.id

                // when
                Mockito.`when`(userService.deleteUser(userId)).thenReturn(false)
                val actions = mockMvc.perform(
                    MockMvcRequestBuilders.delete(inputUri)
                        .param("userId", userId)
                )

                // then
                actions
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError)
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }
}
