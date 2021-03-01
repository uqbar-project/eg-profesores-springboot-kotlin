package com.uqbar.profesores.config.security

import org.springframework.security.core.AuthenticationException
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import java.io.Serializable
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class UnauthorizedEntryPoint : AuthenticationEntryPoint, Serializable {
    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }
}