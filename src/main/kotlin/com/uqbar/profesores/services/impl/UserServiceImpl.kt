package com.uqbar.profesores.services.impl

import com.uqbar.profesores.domain.Rol
import com.uqbar.profesores.domain.Usuario
import com.uqbar.profesores.repos.UserRepository
import java.util.HashSet

import com.uqbar.profesores.serializer.UserDto
import java.util.ArrayList
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.beans.factory.annotation.Autowired
import com.uqbar.profesores.services.UserService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service(value = "userService")
class UserServiceImpl : UserDetailsService, UserService {
    @Autowired
    private val userDao: UserRepository? = null

    @Autowired
    lateinit var bcryptEncoder: BCryptPasswordEncoder

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails? {
        val usuario: Usuario =
            userDao?.findByUsername(username) ?: throw UsernameNotFoundException("Invalid username or password.")
        return User(usuario.username, usuario.password, getAuthority(usuario))
    }

    private fun getAuthority(usuario: Usuario): Set<SimpleGrantedAuthority> {
        val authorities: MutableSet<SimpleGrantedAuthority> = HashSet()
        usuario.roles.forEach { role -> authorities.add(SimpleGrantedAuthority("ROLE_" + role.name)) }
        return authorities
    }

    override fun findAll(): List<Usuario?>? {
        val list: MutableList<Usuario?> = ArrayList<Usuario?>()
        userDao?.findAll()?.iterator()?.forEachRemaining(list::add)
        return list
    }

    override fun findById(id: Long?): Usuario? {
        return userDao!!.findById(id!!).get()
    }

    override fun findOne(username: String?): Usuario? {
        return userDao!!.findByUsername(username)
    }

    override fun save(user: UserDto?): Usuario {
        val nUsuario: Usuario = user!!.usuarioFromDto
        nUsuario.password = bcryptEncoder.encode(user.password)
        return userDao!!.save(nUsuario)
    }
}