package com.example.be.service

import com.example.be.repository.BoardRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class BoardServiceImplTest {

    @InjectMocks
    lateinit var service: BoardService

    @Mock
    lateinit var repository: BoardRepository

    @Nested
    @DisplayName("")
    inner class TestGetAllBoard {

        @Test
        @DisplayName("")
        fun test00() {
            // given
            val pageRequest = PageRequest.of(0, 10)


            // when

            // then

        }
    }

}
