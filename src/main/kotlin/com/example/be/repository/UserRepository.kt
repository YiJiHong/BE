package com.example.be.repository

import com.example.be.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String> {


}
