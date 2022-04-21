package com.example.be.controller

import com.example.be.dto.BoardDto
import com.example.be.dto.InsertBoardDto
import com.example.be.service.BoardService
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
class BoardController(val boardService: BoardService) {

    @GetMapping("/{userEmail}")
    fun getAllBoard(@PathVariable userEmail: String): ResponseEntity<Page<BoardDto>> {
        return ResponseEntity(boardService.getAllBoard(userEmail), HttpStatus.OK)
    }

    @GetMapping()
    fun getBoard(@RequestParam boardId: String): ResponseEntity<BoardDto> {
        return ResponseEntity(boardService.getBoard(boardId), HttpStatus.OK)
    }

    @PostMapping
    fun insertBoard(@RequestBody insertBoardDto: InsertBoardDto): ResponseEntity<Boolean> {
        return ResponseEntity(boardService.insertBoard(insertBoardDto), HttpStatus.OK)
    }

    @PutMapping
    fun updateBoard(@RequestBody insertBoardDto: InsertBoardDto): ResponseEntity<Boolean> {
        return ResponseEntity(boardService.insertBoard(insertBoardDto), HttpStatus.OK)
    }

    @DeleteMapping("/{boardId}")
    fun deleteBoard(@PathVariable boardId: String): ResponseEntity<Boolean> {
        val deleteBoard = boardService.deleteBoard(boardId)

        return if (deleteBoard) {
            ResponseEntity(deleteBoard, HttpStatus.OK)
        }else {
            ResponseEntity(deleteBoard, HttpStatus.NOT_FOUND)
        }
    }
}
