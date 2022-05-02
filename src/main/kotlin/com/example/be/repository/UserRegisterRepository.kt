package com.example.be.repository

import com.example.be.entity.UserRegisterInfo
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRegisterRepository: MongoRepository<UserRegisterInfo, String> {
}
