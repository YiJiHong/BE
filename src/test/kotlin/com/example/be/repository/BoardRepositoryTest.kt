package com.example.be.repository

import com.example.be.entity.Board
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataMongoTest
class BoardRepositoryTest {

    @Autowired
    lateinit var boardRepository: BoardRepository

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

}