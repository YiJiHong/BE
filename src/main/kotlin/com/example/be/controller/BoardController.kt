package com.example.be.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board")
class BoardController {

    fun getBoard(userId: Long): String {
        return "ok!";
    }
}