package com.example.be.controller

import com.example.be.dto.BoardDto
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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
class BoardController(val boardService: BoardService) {

    @GetMapping("/{userEmail}")
    fun getBoard(@PathVariable userEmail: String): ResponseEntity<Page<BoardDto>> {
        return ResponseEntity(boardService.getAllBoard(userEmail), HttpStatus.OK)
    }

    @GetMapping("/count")
    fun getBoardCount() {
        return TODO();
    }

    @PostMapping
    fun insertBoard() {
        return TODO();
    }

    @PutMapping
    fun updateBoard() {
        return TODO();
    }

    @DeleteMapping
    fun deleteBoard() {
        return TODO();
    }

}