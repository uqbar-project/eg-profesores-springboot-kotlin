package com.uqbar.profesores

import com.uqbar.profesores.domain.Materia
import com.uqbar.profesores.domain.Profesor
import com.uqbar.profesores.repos.MateriaRepository
import com.uqbar.profesores.repos.ProfesorRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

// Ejemplo DataJpaTest
@DataJpaTest
class ClaseMateriaRepositoryTest {

    @Autowired
    lateinit var materiaRepository: MateriaRepository

    @Autowired
    lateinit var profesorRepository: ProfesorRepository

    @Test
    fun `al buscar una materia sin profes no me trae nada`() {
        // Arrange
        val materia = materiaRepository.save(Materia(nombre = "EyM", anio = 5))

        // Act
        val materias = materiaRepository.findFullById(materia.id)

        // Assert
        assertTrue(materias.isEmpty())
    }


    @Test
    fun `al buscar una materia con profes hace un producto cartesiano entre materias y profes`() {
        // Arrange
        val materia = materiaRepository.save(Materia(nombre = "PHM", anio = 3))
        profesorRepository.save(Profesor(nombreCompleto = "Fer Dodino").apply {
            agregarMateria(materia)
        })
        profesorRepository.save(Profesor(nombreCompleto = "Juan Contardo").apply {
            agregarMateria(materia)
        })

        // Act
        val filas = materiaRepository.findFullById(materia.id)

        // Assert
        assertEquals(2, filas.size)
    }

}