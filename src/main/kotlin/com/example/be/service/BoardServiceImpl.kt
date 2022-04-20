package com.example.be.service

import com.example.be.dto.BoardDto
import com.example.be.dto.InsertBoardDto
import com.example.be.repository.BoardRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class BoardServiceImpl(val boardRepository: BoardRepository) : BoardService {

    @Transactional(readOnly = true)
    override fun getAllBoard(userEmail: String): Page<BoardDto> {
        TODO("Not yet implemented")
    }

    override fun deleteBoard(boardId: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun insertBoard(insertBoardDto: InsertBoardDto): Boolean {
        TODO("Not yet implemented")
    }

}
