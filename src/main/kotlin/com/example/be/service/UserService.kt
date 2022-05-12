package com.example.be.service

import com.example.be.dto.UpdateUserDto
import com.example.be.dto.UserDto
import com.example.be.dto.UserRegisterDto

interface UserService {
    fun getUserProfile(userId: String): UserDto
    fun register(userRegisterDto: UserRegisterDto): Boolean
    fun login(userRegisterDto: UserRegisterDto): Boolean
    fun changePassword(userRegisterDto: UserRegisterDto): Boolean
    fun updateUserProfile(updateUserDto: UpdateUserDto): Boolean
    fun deleteUser(userId: String): Boolean
    fun checkDuplicateId(userId: String): Boolean
}
