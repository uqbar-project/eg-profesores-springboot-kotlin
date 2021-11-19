package com.uqbar.profesores.controller

import com.uqbar.profesores.repos.MateriaRepository
import com.uqbar.profesores.serializer.MateriaDTO
import com.uqbar.profesores.serializer.ProfesorDTO
import com.uqbar.profesores.service.MateriaService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.GET])
class MateriaController {
    @Autowired
    lateinit var materiaService: MateriaService

    @GetMapping("/materias")
    @ApiOperation("Devuelve todas las materias")
    fun getMaterias() = materiaService.getMaterias()

    @GetMapping("/materias/{id}")
    @ApiOperation("Devuelve una materia, con sus profesores")
    fun getMateria(@PathVariable id: Long) = materiaService.getMateria(id)
}