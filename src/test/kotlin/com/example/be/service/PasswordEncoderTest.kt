package com.example.be.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class PasswordEncoderTest {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Test
    @DisplayName("PasswordEncoder를 통해 password를 encode 할 수 있다.")
    fun test00() {
        // given
        val rawPassword = "testPassword123"

        // when
        val encode = passwordEncoder.encode(rawPassword)

        // then
        Assertions.assertNotEquals(encode, rawPassword)
        println("encode = ${encode}")
    }

    @Nested
    @DisplayName("주어진 rawPassword와 encode된 password의 비교는 PasswordEoncoder.matches를 통해 검증한다.")
    inner class PasswordEncoderMatchesTest {
        @Test
        @DisplayName("encode된 password와 rawPassword를 econde한 결과가 같으면 true를 반환한다.")
        fun test00() {
            // given
            val rawPassword = "testPassword123"
            val encode = passwordEncoder.encode(rawPassword)

            // when
            // then
            Assertions.assertTrue(passwordEncoder.matches(rawPassword, encode))
            println("encode = ${encode}")
        }

        @Test
        @DisplayName("encode된 password와 rawPassword를 econde한 결과가 다르면 true를 반환한다.")
        fun test01() {
            // given
            val rawPassword = "testPassword123"
            val differentPassword = "wrong"
            val encode = passwordEncoder.encode(differentPassword)

            // when
            // then
            Assertions.assertFalse(passwordEncoder.matches(rawPassword, encode))
            println("encode = ${encode}")
        }
    }
}
