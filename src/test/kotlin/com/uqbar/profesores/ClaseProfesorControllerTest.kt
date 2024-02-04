package com.uqbar.profesores

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.uqbar.profesores.domain.Materia
import com.uqbar.profesores.domain.Profesor
import com.uqbar.profesores.repos.MateriaRepository
import com.uqbar.profesores.repos.ProfesorRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

// Ejemplo SpringbootTest

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Dado un controller de profes")
class ClaseProfesorControllerTest {

    private val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var materiaRepository: MateriaRepository

    @Autowired
    lateinit var profesorRepository: ProfesorRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    var idProfesor: Long? = null

    @BeforeEach
    fun `setupear profes`() {
        // Arrange
        val materia = materiaRepository.save(Materia(nombre = "Algo3", anio = 2))
        val materia1 = materiaRepository.save(Materia(nombre = "PHM", anio = 3))
        val profesor = profesorRepository.save(Profesor(nombreCompleto = "Fer Dodino").apply {
            agregarMateria(materia)
            agregarMateria(materia1)
        })

        idProfesor = profesor.id
    }

    @AfterEach
    fun `limpio info`() {
        profesorRepository.deleteAll()
        materiaRepository.deleteAll()
    }

    @Test
    fun `al pedir la info de un profe trae sus materias`() {
        // Act
        val responseEntity = mockMvc.perform(get("/profesores/${idProfesor}"))
            .andReturn()
            .response

        assertEquals(200, responseEntity.status)

        println(responseEntity.contentAsString)

        val profesorDelEndpoint = mapper.readValue<Profesor>(responseEntity.contentAsString)

        assertEquals(setOf("PHM", "Algo3"), profesorDelEndpoint.materias.map { it.nombre }.toSet())
    }
}