package com.uqbar.profesores.controller

import com.uqbar.profesores.service.MateriaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.GET])
@RequestMapping("/materias")
class MateriaController {
    @Autowired
    lateinit var materiaService: MateriaService

    @GetMapping("")
    fun getMaterias() =
        materiaService.getMaterias().toList()

    @GetMapping("/{id}")
    fun getMateria(@PathVariable id: Long) =
        materiaService.getMateria(id)
}