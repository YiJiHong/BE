package com.example.be.controller

import com.example.be.service.BoardService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
class BoardController(val boardService: BoardService) {

    @GetMapping
    fun getBoard(userId: Long): String {
        return "ok!";
    }

    @GetMapping("/count")
    fun getBoardCOunt() {
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