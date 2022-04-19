package com.example.be.repository

import com.example.be.entity.Board
import org.springframework.data.repository.CrudRepository

interface BoardRepository: CrudRepository<Board, String> {

    fun findAllByUserEmail(userEmail: String): Board
}
