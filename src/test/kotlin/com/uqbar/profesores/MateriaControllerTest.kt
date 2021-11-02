package com.uqbar.profesores

import com.jayway.jsonpath.spi.json.JsonOrgJsonProvider
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de materias")
class MateriaControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `obtener todas las materias`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/materias"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
    }

    @Test
    fun `al buscar la informacion de una materia que no existe recibimos como respuesta un codigo de http 404`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/materias/12424"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}