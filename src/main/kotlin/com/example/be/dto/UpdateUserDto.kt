package com.example.be.dto

data class UpdateUserDto(
    val name: String,
    val nickName: String,
    val intro: String?,
    val profileImage: String?,
    val scraps: List<String>,
    val likes: List<String>,
    val followers: List<String>,
    val followings: List<String>,
    val tags: List<String>
) {
}
