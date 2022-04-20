package com.example.be.service

import com.example.be.dto.BoardDto
import org.springframework.data.domain.Page

interface BoardService {
    fun getAllBoard(userEmail: String): Page<BoardDto>
}
