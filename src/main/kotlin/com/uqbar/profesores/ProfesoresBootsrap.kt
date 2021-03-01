package com.uqbar.profesores

import com.uqbar.profesores.domain.Materia
import com.uqbar.profesores.domain.Profesor
import com.uqbar.profesores.domain.Rol
import com.uqbar.profesores.domain.Usuario
import com.uqbar.profesores.repos.MateriaRepository
import com.uqbar.profesores.repos.ProfesorRepository
import com.uqbar.profesores.repos.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

/**
 *
 * Para explorar otras opciones
 * https://stackoverflow.com/questions/38040572/spring-boot-loading-initial-data
 */
@Service
class ProfesoresBootstrap : InitializingBean {
    @Autowired
    lateinit var repoMaterias: MateriaRepository

    @Autowired
    lateinit var repoProfes : ProfesorRepository

    @Autowired
    lateinit var repoUsuarios : UserRepository

    @Autowired
    lateinit var bcryptEncoder: BCryptPasswordEncoder

    override fun afterPropertiesSet() {
        println("************************************************************************")
        println("Running initialization")
        println("************************************************************************")
        init()
    }

    fun init() {
        val	algoritmos = Materia("Algoritmos y Estructura de Datos", 1)
        val paradigmas = Materia("Paradigmas de Programacion", 2)
        val disenio = Materia("Diseño de Sistemas", 3)

        repoMaterias.save(algoritmos)
        repoMaterias.save(paradigmas)
        repoMaterias.save(disenio)

        val spigariol = Profesor("Lucas Spigariol")
        spigariol.agregarMateria(algoritmos)
        spigariol.agregarMateria(paradigmas)

        val passerini = Profesor("Nicolás Passerini")
        passerini.agregarMateria(paradigmas)
        passerini.agregarMateria(disenio)

        val dodino = Profesor("Fernando Dodino")
        dodino.agregarMateria(disenio)

        repoProfes.save(spigariol)
        repoProfes.save(passerini)
        repoProfes.save(dodino)

        val profesorRol = Rol("PROFESOR", "Rol de profesor")
        val alumnoRol = Rol("ALUMNO", "Rol de alumno")

        val profesor = Usuario("profesor", bcryptEncoder.encode("password"), setOf(profesorRol, alumnoRol))
        val alumno = Usuario("alumno", bcryptEncoder.encode("password"), setOf(alumnoRol))

        repoUsuarios.save(profesor)
        repoUsuarios.save(alumno)
    }
}