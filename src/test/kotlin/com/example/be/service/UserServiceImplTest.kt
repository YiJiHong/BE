package com.example.be.service

import com.example.be.Fixture
import com.example.be.dto.UserRegisterDto
import com.example.be.entity.UserRegisterInfo
import com.example.be.exception.NoneUserException
import com.example.be.repository.UserRegisterRepository
import com.example.be.repository.UserRepository
import com.nhaarman.mockitokotlin2.any
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class UserServiceImplTest {

    @InjectMocks
    lateinit var service: UserServiceImpl

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var userRegisterRepository: UserRegisterRepository

    @Mock
    lateinit var passwordEncoder: PasswordEncoder

    @Nested
    @DisplayName("User의 정보를 가져올 때,")
    inner class GetProfileUserRegisterInfoTest {
        @Test
        @DisplayName("userId에 대응되는 User를 UserDto로 반환한다.")
        fun test00() {
            // given
            val userId = Fixture.userDto.id
            val user = Fixture.user

            // when
            Mockito.`when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
            val userDto = service.getUserProfile(userId)

            // then
            Assertions.assertThat(userDto).usingRecursiveComparison().isEqualTo(user.toDataModel())
        }

        @Test
        @DisplayName("userId에 대응되는 User가 없는 경우, NoneUserException을 던진다.")
        fun test01() {
            // given
            val userId = "None"

            // when
            Mockito.`when`(userRepository.findById(userId)).thenReturn(Optional.empty())

            // then
            assertThrows(NoneUserException::class.java) { service.getUserProfile(userId) }
        }
    }

    @Nested
    @DisplayName("User를 등록할 때,")
    inner class RegisterTest {
        @Test
        @DisplayName("user등록에 성공하면, true를 반환한다.")
        fun test00() {
            // given
            val userRegisterDto = Fixture.userRegisterDto
            val userRegisterInfo = UserRegisterInfo(
                id = userRegisterDto.email,
                password = "encoded password"
            )

            // when
            Mockito.`when`(passwordEncoder.encode(Mockito.anyString())).thenReturn("encoded password")
            Mockito.`when`(userRegisterRepository.save(any())).thenReturn(userRegisterInfo)
            val result = service.register(userRegisterDto)

            // then
            assertTrue(result)
        }

        @ParameterizedTest
        @CsvSource(
            "12345abcdefghijklmnop@naver.com, test123",
            "test@naver.com, wrongpassword0123456789"
        )
        @DisplayName("email혹은 비밀번호의 검증에 실패하면, false를 반환한다.")
        fun test01(inputEmail: String, inputPassword: String) {
            // given
            val userRegisterDto = UserRegisterDto(
                email = inputEmail,
                password = inputPassword
            )

            // when
            // then
            val result: Boolean = service.register(userRegisterDto)
            assertFalse(result)
        }
    }

    @Nested
    @DisplayName("로그인 할 때,")
    inner class LoginTest {
        @Test
        @DisplayName("성공하면, true를 반환한다.")
        fun test00() {
            // given
            val userRegisterDto = Fixture.userRegisterDto
            val userRegisterInfo = UserRegisterInfo(
                id = userRegisterDto.email,
                password = "encoded password"
            )

            // when
            Mockito.`when`(userRegisterRepository.findById(Mockito.anyString())).thenReturn(Optional.of(userRegisterInfo))
            Mockito.`when`(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true)
            val result = service.login(userRegisterDto)

            // then
            assertTrue(result)
        }

        @Test
        @DisplayName("비밀번호가 틀리면, false를 반환한다.")
        fun test01() {
            // given
            val userRegisterDto = Fixture.userRegisterDto
            val userRegisterInfo = UserRegisterInfo(
                id = userRegisterDto.email,
                password = "encoded password"
            )

            // when
            Mockito.`when`(userRegisterRepository.findById(Mockito.anyString())).thenReturn(Optional.of(userRegisterInfo))
            Mockito.`when`(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(false)

            // then
            val result = service.login(userRegisterDto)
            assertFalse(result)
        }

        @Test
        @DisplayName("id가 틀리면, false를 반환한다.")
        fun test02() {
            // given
            val userRegisterDto = Fixture.userRegisterDto

            // when
            Mockito.`when`(userRegisterRepository.findById(Mockito.anyString())).thenReturn(Optional.empty())

            // then
            val result = service.login(userRegisterDto)
            assertFalse(result)
        }

    }

}
