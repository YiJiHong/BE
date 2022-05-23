package com.example.be.service

import com.example.be.dto.UpdateUserDto
import com.example.be.dto.UserDto
import com.example.be.dto.UserRegisterDto
import com.example.be.entity.UserRegisterInfo
import com.example.be.exception.NoneUserException
import com.example.be.exception.NotValidUserRegisterFormException
import com.example.be.repository.UserRepository
import com.example.be.repository.UserRegisterRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class UserServiceImpl(
    val userRepository: UserRepository,
    val userRegisterRepository: UserRegisterRepository,
    val passwordEncoder: PasswordEncoder): UserService {

    companion object {
        private const val MAX_ID_LENGTH = 15
        private const val MIN_ID_LENGTH = 5
        private const val MAX_PASSWORD_LENGTH = 20
        private const val MIN_PASSWORD_LENGTH = 8
    }

    @Transactional(readOnly = true)
    override fun getUserProfile(userId: String): UserDto {
        val findById = userRepository.findById(userId)
        if (findById.isPresent) {
            return findById.get().toDataModel()
        } else {
            throw NoneUserException("No User. id = ${userId}")
        }
    }

    @Transactional
    override fun register(userRegisterDto: UserRegisterDto): Boolean {
        return if (checkValid(userRegisterDto)) {
            val userRegisterInfoData = UserRegisterInfo(
                id = userRegisterDto.email,
                password = passwordEncoder.encode(userRegisterDto.password)
            )
            userRegisterRepository.save(userRegisterInfoData)
            true
        } else {
            throw NotValidUserRegisterFormException("Not Valid UserRegisterForm : ${userRegisterDto}" ,userRegisterDto)
        }
    }

    @Transactional(readOnly = true)
    override fun login(userRegisterDto: UserRegisterDto): Boolean {

        val findById = userRegisterRepository.findById(userRegisterDto.email)

        return if (findById.isPresent) {
            passwordEncoder.matches(userRegisterDto.password, findById.get().password)
        } else {
            false
        }
    }

    override fun changePassword(userRegisterDto: UserRegisterDto): Boolean {
        return if (checkValid(userRegisterDto)) {
            val user = UserRegisterInfo(
                id = userRegisterDto.email,
                password = passwordEncoder.encode(userRegisterDto.password)
            )
            userRegisterRepository.save(user)
            true
        } else {
            throw NotValidUserRegisterFormException("Not Valid UserRegisterForm : ${userRegisterDto}" ,userRegisterDto)
        }
    }

    override fun updateUserProfile(updateUserDto: UpdateUserDto): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteUser(userRegisterDto: UserRegisterDto): Boolean {
        return if (userRepository.findByEmail(userRegisterDto.email) != null && userRegisterRepository.findById(userRegisterDto.email).isPresent) {
            userRegisterRepository.deleteById(userRegisterDto.email)
            userRepository.deleteUserByEmail(userRegisterDto.email)
            true
        } else {
            false
        }
    }

    override fun checkDuplicateId(userEmail: String): Boolean {
        return userRegisterRepository.findById(userEmail).isPresent
    }

    private fun checkValid(userRegisterDto: UserRegisterDto): Boolean {
        val lastIndexOf: Int = userRegisterDto.email.lastIndexOf('@')
        val id = userRegisterDto.email.substring(0, lastIndexOf)

        return (id.length in MIN_ID_LENGTH .. MAX_ID_LENGTH) &&
                (userRegisterDto.password.length in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH)
    }
}
