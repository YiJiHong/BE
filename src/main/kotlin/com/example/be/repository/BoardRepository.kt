package com.example.be.repository

import com.example.be.entity.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface BoardRepository: MongoRepository<Board, String> {

    fun findAllByUserEmail(pageable: Pageable, userEmail: String): Page<Board>
}
