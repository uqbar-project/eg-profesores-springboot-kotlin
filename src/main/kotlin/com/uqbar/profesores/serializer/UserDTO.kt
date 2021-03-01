package com.uqbar.profesores.serializer

import com.uqbar.profesores.domain.Usuario

class UserDto {
    var username: String? = null
    var password: String? = null
    val usuarioFromDto: Usuario

    get() {
        val user = Usuario()
        user.username = username
        user.password = password
        return user
    }
}