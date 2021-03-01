package com.uqbar.profesores.services

import com.uqbar.profesores.domain.Usuario
import com.uqbar.profesores.serializer.UserDto

interface UserService {
    fun save(user: UserDto?): Usuario?
    fun findAll(): List<Usuario?>?
    fun findOne(username: String?): Usuario?
    fun findById(id: Long?): Usuario?
}