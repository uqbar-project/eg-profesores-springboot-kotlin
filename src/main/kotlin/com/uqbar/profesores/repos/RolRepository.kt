package com.uqbar.profesores.repos

import com.uqbar.profesores.domain.Rol
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RolRepository : CrudRepository<Rol?, Long?>