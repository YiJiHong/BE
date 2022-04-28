package com.example.be.exception

data class NoneBoardException(val msg: String): RuntimeException(msg)
data class NoneUserException(val msg: String): RuntimeException(msg)
