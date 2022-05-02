package com.example.be.entity

import com.example.be.dto.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document
class User(
    @Id
    val id: String?,
    val email: String,
    val name: String,
    @Field("nickname")
    val nickName: String,
    val intro: String?,
    val profileImage: String?,
    val scraps: List<String>,
    val likes: List<String>,
    val followers: List<String>,
    val followings: List<String>,
    val tags: List<String>
) {
    fun toDataModel(): UserDto {
        return UserDto(
            id = this.id ?: "",
            email = this.email,
            name = this.name,
            nickName = this.nickName,
            intro = this.intro,
            profileImage = this.profileImage,
            scraps = this.scraps,
            likes = this.likes,
            followers = this.followers,
            followings = this.followings,
            tags = this.tags
        )
    }

}
