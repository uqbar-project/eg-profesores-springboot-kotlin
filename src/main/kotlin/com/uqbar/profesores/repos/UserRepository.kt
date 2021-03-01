package com.uqbar.profesores.repos

import com.uqbar.profesores.domain.Usuario
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<Usuario?, Long?> {
    fun findByUsername(username: String?): Usuario?
}