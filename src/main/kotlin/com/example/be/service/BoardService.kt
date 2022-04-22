package com.example.be.service

import com.example.be.dto.BoardDto
import com.example.be.dto.InsertBoardDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardService {
    fun getAllBoard(pageable: Pageable, userEmail: String): Page<BoardDto>
    fun getBoard(boardId: String): BoardDto
    fun deleteBoard(boardId: String): Boolean
    fun insertBoard(insertBoardDto: InsertBoardDto): Boolean
}
