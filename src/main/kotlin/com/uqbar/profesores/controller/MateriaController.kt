package com.uqbar.profesores.controller

import com.uqbar.profesores.service.MateriaService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.GET])
@RequestMapping("/materias")
class MateriaController {
    @Autowired
    lateinit var materiaService: MateriaService

    @GetMapping("")
    @Operation(summary = "Devuelve todas las materias")
    fun getMaterias() = materiaService.getMaterias().toList()

    @GetMapping("/{id}")
    @Operation(summary = "Devuelve una materia, con sus profesores")
    fun getMateria(@PathVariable id: Long) = materiaService.getMateria(id)
}