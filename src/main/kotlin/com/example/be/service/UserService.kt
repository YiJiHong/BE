package com.example.be.service

import com.example.be.dto.UpdateUserDto
import com.example.be.dto.UserDto
import com.example.be.dto.UserRegisterDto

interface UserService {
    fun getUserProfile(userId: String): UserDto
    fun register(userRegisterDto: UserRegisterDto): Boolean
    fun login(userId: String, password: String): Boolean
    fun changePassword(updateUserDto: UpdateUserDto): Boolean
    fun deleteUser(userId: String): Boolean
    fun checkDuplicateId(userId: String): Boolean
}
