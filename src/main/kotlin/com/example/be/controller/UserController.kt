package com.example.be.controller

import com.example.be.dto.UserDto
import com.example.be.dto.UserRegisterDto
import com.example.be.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserController (val userService: UserService){

    @GetMapping
    fun getUserProfile(@RequestParam userId: String): ResponseEntity<UserDto> {
        return ResponseEntity(userService.getUserProfile(userId), HttpStatus.OK)
    }

    @GetMapping("/login")
    fun login(@RequestParam userId: String, @RequestParam password: String): ResponseEntity<Boolean> {
        return if (userService.login(userId, password)) {
            ResponseEntity(true, HttpStatus.OK)
        } else {
            ResponseEntity(false, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("")
    fun registerUser(@RequestBody userRegisterDto: UserRegisterDto): ResponseEntity<Boolean> {
        return if (userService.register(userRegisterDto)) {
            ResponseEntity(true, HttpStatus.OK)
        } else {
            ResponseEntity(false, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}
