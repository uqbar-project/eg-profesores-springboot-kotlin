package com.uqbar.profesores

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.uqbar.profesores.domain.Profesor
import com.uqbar.profesores.repos.MateriaRepository
import com.uqbar.profesores.repos.ProfesorRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de profesores")
class ProfesorControllerTest {
    private val ID_PROFESOR = 1L
    private val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var repoProfes: ProfesorRepository

    @Autowired
    lateinit var repoMaterias: MateriaRepository

    @Test
    fun `podemos consultar todos los profesores`() {
        val responseEntity = mockMvc
            .perform(MockMvcRequestBuilders.get("/profesores"))
            .andReturn().response
        val profesores = mapper.readValue<List<Profesor>>(responseEntity.contentAsString)
        assertEquals(200, responseEntity.status)
        assertEquals(3, profesores.size)
        // los profesores no traen las materias
        assertEquals(0, profesores.first().materias.size)
    }

    @Test
    fun `al traer el dato de un profesor trae las materias en las que participa`() {
        val responseEntity = mockMvc.perform(MockMvcRequestBuilders.get("/profesores/$ID_PROFESOR")).andReturn().response
        assertEquals(200, responseEntity.status)
        val profesor = mapper.readValue<Profesor>(responseEntity.contentAsString)
        assertEquals(2, profesor.materias.size)
    }

    @Test
    fun `no podemos traer informacion de un profesor inexistente`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/profesores/100"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    @Transactional
    fun `podemos actualizar la informacion de un profesor`() {
        val profesor = getProfesor(ID_PROFESOR)
        val materias = repoMaterias.findByNombre("Diseño de Sistemas")
        assertEquals(1, materias.size)
        val materiaNueva = materias.first()
        profesor.agregarMateria(materiaNueva)
        updateProfesor(ID_PROFESOR, profesor)
        val nuevoProfesor = getProfesor(ID_PROFESOR)
        val materiasDelProfesor = profesor.materias.size
        assertEquals(materiasDelProfesor, nuevoProfesor.materias.size)
    }

    @Test
    fun `no podemos actualizar un profesor inexistente`() {
        val profesorBody = mapper.writeValueAsString(Profesor().apply {
            nombreCompleto = "Juan Contardo"
            id = 100
        })
        mockMvc.perform(MockMvcRequestBuilders.put("/profesores/100")
            .contentType("application/json")
            .content(profesorBody)
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    private fun updateProfesor(idProfesor: Long, profesor: Profesor) {
        val profesorBody = mapper.writeValueAsString(profesor)
        mockMvc.perform(MockMvcRequestBuilders.put("/profesores/$idProfesor")
                .contentType("application/json")
                .content(profesorBody)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    private fun getProfesor(idProfesor: Long): Profesor =
        repoProfes.findById(idProfesor).orElseThrow { RuntimeException("Profesor con identificador $idProfesor no existe") }
}