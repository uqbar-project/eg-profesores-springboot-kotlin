package com.uqbar.profesores.controller

import com.uqbar.profesores.domain.Profesor
import com.uqbar.profesores.serializer.ProfesorBasicoDTO
import com.uqbar.profesores.service.ProfesorService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/profesores")
class ProfesorController {
    @Autowired
    lateinit var profesorService: ProfesorService

    @GetMapping("")
    @Operation(summary = "Devuelve todos los profesores, sin las materias")
    fun getProfesores() = profesorService.getProfesores().map { ProfesorBasicoDTO(it) }.toList()

    @GetMapping("/{id}")
    @Operation(summary = "Devuelve un profesor, con sus materias")
    fun getProfesor(@PathVariable id: Long) = profesorService.getProfesor(id)

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un profesor")
    fun actualizarProfesor(@RequestBody profesorNuevo: Profesor, @PathVariable id: Long) =
        profesorService.actualizarProfesor(profesorNuevo, id)
}