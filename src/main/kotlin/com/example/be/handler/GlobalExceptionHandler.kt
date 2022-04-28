package com.example.be.handler

import com.example.be.dto.ErrorResponse
import com.example.be.exception.NoneBoardException
import com.example.be.exception.NoneUserException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoneBoardException::class)
    fun handleInvalidDtoException(e: NoneBoardException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(msg = e.msg), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(e.toString()), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoneUserException::class)
    fun handleNoneUserException(e: NoneUserException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(msg = e.msg), HttpStatus.NOT_FOUND)
    }

}
