package com.uqbar.profesores.controller

import com.uqbar.profesores.domain.AuthToken
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import com.uqbar.profesores.domain.LoginUser
import com.uqbar.profesores.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import com.uqbar.profesores.config.security.TokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/token")
class AuthenticationController {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtTokenUtil: TokenProvider

    @RequestMapping(value = ["/generate-token"], method = [RequestMethod.POST])
    @Throws(AuthenticationException::class)
    fun register(@RequestBody loginUser: LoginUser): ResponseEntity<*> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginUser.username,
                loginUser.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtTokenUtil.generateToken(authentication)
        return ResponseEntity.ok(AuthToken(token))
    }
}