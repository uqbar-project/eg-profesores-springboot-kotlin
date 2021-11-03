package com.uqbar.profesores.controller

import com.uqbar.profesores.domain.Profesor
import com.uqbar.profesores.repos.ProfesorRepository
import com.uqbar.profesores.serializer.ProfesorBasicoDTO
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@CrossOrigin(origins = ["*"])
class ProfesorController {
    @Autowired
    lateinit var profesorRepository: ProfesorRepository

    @GetMapping("/profesores")
    @ApiOperation("Devuelve todos los profesores, sin las materias")
    fun getProfesores() = this.profesorRepository.findAll().map { ProfesorBasicoDTO(it) }.toList()

    @GetMapping("/profesores/{id}")
    @ApiOperation("Devuelve un profesor, con sus materias")
    fun getProfesor(@PathVariable id: Long) =
        this.profesorRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "El profesor con identificador $id no existe")
        }

    @PutMapping("/profesores/{id}")
    @ApiOperation("Actualiza un profesor")
    fun actualizarProfesor(@RequestBody profesorNuevo: Profesor, @PathVariable id: Long): ResponseEntity<String> {
        profesorRepository.findById(id).map {
            it.nombreCompleto = profesorNuevo.nombreCompleto
            it.materias = profesorNuevo.materias
            profesorRepository.save(it)
        }.orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "El profesor con identificador $id no existe")
        }

        return ResponseEntity.ok().body("El profesor fue actualizado correctamente")
    }
}