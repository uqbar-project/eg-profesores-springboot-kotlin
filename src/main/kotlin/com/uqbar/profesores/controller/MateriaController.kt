package com.uqbar.profesores.controller

import com.uqbar.profesores.repos.MateriaRepository
import com.uqbar.profesores.serializer.MateriaDTO
import com.uqbar.profesores.serializer.ProfesorDTO
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
    lateinit var materiaRepository: MateriaRepository

    @GetMapping("/materias")
    @ApiOperation("Devuelve todas las materias")
    fun getZonas() = materiaRepository.findAll()

    @GetMapping("/materias/{id}")
    @ApiOperation("Devuelve una materia, con sus profesores")
    fun getMateria(@PathVariable id: Long): ResponseEntity<MateriaDTO> {
        // Recibimos n registros de materias
        val materiasDTO = materiaRepository.findFullById(id)
        if (materiasDTO.isEmpty()) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "La materia con identificador $id no existe")
        }

        // Agrupamos los profesores de la materia
        val materia = materiasDTO.first()
        val profesores = materiasDTO.map { ProfesorDTO(it.getProfesorId(), it.getProfesorNombre()) }
        return ResponseEntity.ok().body(MateriaDTO(materia.getId(), materia.getNombreLindo(), materia.getAnio(), profesores))
    }
}