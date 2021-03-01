package com.uqbar.profesores.config.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.SignatureException
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter
import javax.annotation.Resource

class JwtAuthenticationFilter : OncePerRequestFilter() {
    @Resource(name = "userService")
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    lateinit var jwtTokenUtil: TokenProvider

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        val header = req.getHeader("Authorization")
        var username: String? = null
        var authToken: String? = null
        if (header != null && header.startsWith("Bearer ")) {
            authToken = header.replace("Bearer ", "")
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken)
            } catch (e: IllegalArgumentException) {
                logger.error("An error occurred while fetching Username from Token", e)
            } catch (e: ExpiredJwtException) {
                logger.warn("The token has expired", e)
            } catch (e: SignatureException) {
                logger.error("Authentication Failed. Username or Password not valid.")
            }
        } else {
            logger.warn("Couldn't find bearer string, header will be ignored")
        }
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                val authentication = jwtTokenUtil.getAuthentication(
                    authToken,
                    SecurityContextHolder.getContext().authentication,
                    userDetails
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(req)
                logger.info("authenticated user $username, setting security context")
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        chain.doFilter(req, res)
    }
}