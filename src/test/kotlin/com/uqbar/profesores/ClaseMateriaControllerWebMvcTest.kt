package com.uqbar.profesores

import com.uqbar.profesores.controller.MateriaController
import com.uqbar.profesores.errorHandling.NotFoundException
import com.uqbar.profesores.service.MateriaService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

// Ejemplo WebMvcTest
@WebMvcTest(controllers = [MateriaController::class])
open class ClaseMateriaControllerWebMvcTest {

    @MockBean
    lateinit var materiaService: MateriaService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    open fun `al pedir info de una materia que no existe devuelve 404`() {
        val idMateria = 1L

        Mockito.`when`(materiaService.getMateria(idMateria))
            .thenAnswer { throw NotFoundException("Materia no existe") }

        mockMvc.perform(get("/materias/${idMateria}"))
            .andExpect(status().isNotFound)
    }
}

