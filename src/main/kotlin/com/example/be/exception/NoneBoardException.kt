package com.example.be.exception

import com.example.be.dto.BoardDto

data class NoneBoardException(val msg: String, val board: BoardDto): RuntimeException(msg)
