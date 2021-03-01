package com.uqbar.profesores.config.security

import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.util.stream.Collectors
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.GrantedAuthority
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*
import java.util.function.Function
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.JwtParser


@Component
class TokenProvider : Serializable {
    fun getUsernameFromToken(token: String?): String {
        return getClaimFromToken(token) { obj: Claims -> obj.subject }
    }

    fun getExpirationDateFromToken(token: String?): Date {
        return getClaimFromToken(
            token
        ) { obj: Claims -> obj.expiration }
    }

    fun <T> getClaimFromToken(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parser()
            .setSigningKey("profesores-spring-boot-kotlin")
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String?): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(authentication: Authentication): String {
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))
        return Jwts.builder()
            .setSubject(authentication.name)
            .claim("roles", authorities)
            .signWith(SignatureAlgorithm.HS256, "profesores-spring-boot-kotlin")
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 5*60*60 * 1000))
            .compact()
    }

    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    fun getAuthentication(
        token: String?,
        existingAuth: Authentication?,
        userDetails: UserDetails?
    ): UsernamePasswordAuthenticationToken {
        val jwtParser: JwtParser = Jwts.parser().setSigningKey("profesores-spring-boot-kotlin")
        val claimsJws = jwtParser.parseClaimsJws(token)
        val claims = claimsJws.body
        val authorities: Collection<GrantedAuthority> =
            Arrays.stream(claims["roles"].toString().split(",").toTypedArray())
                .map { role: String? -> SimpleGrantedAuthority(role) }
                .collect(Collectors.toList())
        return UsernamePasswordAuthenticationToken(userDetails, "", authorities)
    }
}