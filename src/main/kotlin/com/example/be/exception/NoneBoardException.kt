package com.example.be.exception

import com.example.be.dto.UserRegisterDto

data class NoneBoardException(val msg: String): RuntimeException(msg)
data class NoneUserException(val msg: String): RuntimeException(msg)
data class NotValidUserRegisterFormException(val msg: String, val userRegisterDto: UserRegisterDto): RuntimeException(msg)
