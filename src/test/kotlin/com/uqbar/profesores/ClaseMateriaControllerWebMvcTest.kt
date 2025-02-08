package com.uqbar.profesores

import com.uqbar.profesores.controller.MateriaController
import com.uqbar.profesores.errorHandling.NotFoundException
import com.uqbar.profesores.service.MateriaService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

// Ejemplo WebMvcTest
@WebMvcTest(controllers = [MateriaController::class])
open class ClaseMateriaControllerWebMvcTest {

    @MockitoBean
    lateinit var materiaService: MateriaService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    open fun `al pedir info de una materia que no existe, debe devolver 404`() {
        Mockito.`when`(materiaService.getMateria(1)).thenAnswer { throw NotFoundException("Materia no existe") }

        mockMvc.perform(get("/materias/1")).andExpect(status().isNotFound)
    }
}

