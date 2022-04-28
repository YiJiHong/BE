package com.example.be.service

import com.example.be.dto.UserDto

interface UserService {
    fun getUser(userId: String): UserDto
}
