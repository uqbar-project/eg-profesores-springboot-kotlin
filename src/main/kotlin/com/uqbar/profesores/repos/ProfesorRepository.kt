package com.uqbar.profesores.repos

import com.uqbar.profesores.domain.Profesor
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ProfesorRepository : CrudRepository<Profesor, Long> {
    @EntityGraph(attributePaths = ["materias"])
    override fun findById(id: Long): Optional<Profesor>
}