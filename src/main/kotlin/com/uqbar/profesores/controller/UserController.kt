package com.uqbar.profesores.controller

import com.uqbar.profesores.domain.Usuario
import com.uqbar.profesores.serializer.UserDto
import org.springframework.security.access.prepost.PreAuthorize
import com.uqbar.profesores.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
class UserController {
    @Autowired
    lateinit var userService: UserService

    @PreAuthorize("hasRole('PROFESOR')")
    @RequestMapping(value = ["/users"], method = [RequestMethod.GET])
    fun listUser(): List<Usuario?>? {
        return userService.findAll()
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @RequestMapping(value = ["/users/{id}"], method = [RequestMethod.GET])
    fun getOne(@PathVariable(value = "id") id: Long?): Usuario? {
        return userService.findById(id)
    }

    @RequestMapping(value = ["/signup"], method = [RequestMethod.POST])
    fun saveUser(@RequestBody user: UserDto?): Usuario? {
        return userService.save(user)
    }
}