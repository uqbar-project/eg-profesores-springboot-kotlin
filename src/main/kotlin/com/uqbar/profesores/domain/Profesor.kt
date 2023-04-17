package com.uqbar.profesores.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany


@Entity
class Profesor(@Column var nombreCompleto: String = "") {
    @Id
    // El GenerationType asociado a la TABLE es importante para tener
    // una secuencia de identificadores única para los profesores
    // (para que no dependa de otras entidades anteriormente creadas)
    // @GeneratedValue(strategy = GenerationType.TABLE)
    @GeneratedValue
    var id: Long = 0

    @ManyToMany(fetch = FetchType.LAZY)
    var materias: MutableSet<Materia> = HashSet()

    fun agregarMateria(materia: Materia) {
        materias.add(materia)
    }
}