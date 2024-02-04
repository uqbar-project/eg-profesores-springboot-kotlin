package com.uqbar.profesores.controller

import com.uqbar.profesores.domain.Profesor
import com.uqbar.profesores.serializer.ProfesorBasicoDTO
import com.uqbar.profesores.service.ProfesorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/profesores")
class ProfesorController {
    @Autowired
    lateinit var profesorService: ProfesorService

    @GetMapping("")
    fun getProfesores() = profesorService.getProfesores().map { ProfesorBasicoDTO(it) }.toList()

    @GetMapping("/{id}")
    fun getProfesor(@PathVariable id: Long) = profesorService.getProfesor(id)

    @PutMapping("/{id}")
    fun actualizarProfesor(@RequestBody profesorNuevo: Profesor, @PathVariable id: Long) =
        profesorService.actualizarProfesor(profesorNuevo, id)
}