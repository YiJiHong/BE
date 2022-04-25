package com.example.be.service

import com.example.be.dto.BoardDto
import com.example.be.dto.InsertBoardDto
import com.example.be.dto.UpdateBoardDto
import com.example.be.entity.Board
import com.example.be.exception.NoneBoardException
import com.example.be.repository.BoardRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class BoardServiceImpl(val boardRepository: BoardRepository) : BoardService {

    private final val SUCCESS = 1L

    @Transactional(readOnly = true)
    override fun getAllBoard(pageable: Pageable, userEmail: String): Page<BoardDto> {
        return boardRepository.findAllByUserEmail(pageable = pageable, userEmail = userEmail)
            .map {
                board: Board -> board.toDataModel()
            }
    }

    @Transactional(readOnly = true)
    override fun getBoard(boardId: String): BoardDto {
        return boardRepository.findBoardById(boardId)?.toDataModel()
            ?: throw NoneBoardException("Not Exists Board. BoardId = ${boardId}")
    }

    @Transactional
    override fun deleteBoard(boardId: String): Boolean {
        if (boardRepository.deleteBoardById(boardId) == SUCCESS) {
            return true
        } else {
            throw NoneBoardException("Not Exists Board. BoardId = ${boardId}")
        }
    }

    @Transactional
    override fun insertBoard(insertBoardDto: InsertBoardDto): Boolean {
        val save: Board = boardRepository.save(insertBoardDto.toEntity())
        return save.id != null
    }

    @Transactional
    override fun updateBoard(updateBoardDto: UpdateBoardDto): Boolean {
        val board = (boardRepository.findBoardById(updateBoardDto.id)
            ?: throw NoneBoardException("Not Exists Board. BoardId = ${updateBoardDto.id}"))

        val newBoard = Board(
            id = updateBoardDto.id,
            userEmail = board.userEmail,
            nickName = board.nickName,
            subTitle = updateBoardDto.subTitle,
            titleImage = updateBoardDto.titleImage,
            likes = board.likes,
            modDateTime = updateBoardDto.modDateTime,
            contents = updateBoardDto.contents.map { contentDto -> contentDto.toEntity() },
            comments = updateBoardDto.comments.map { commentDto -> commentDto.toEntity() }
        )

        val save = boardRepository.save(newBoard)
        return save.id != null
    }
}
