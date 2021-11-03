package com.uqbar.profesores.domain

import javax.persistence.*

@Entity
class Profesor(@Column var nombreCompleto: String = "") {
    @Id
    // El GenerationType asociado a la TABLE es importante para tener
    // una secuencia de identificadores única para los profesores
    // (para que no dependa de otras entidades anteriormente creadas)
    @GeneratedValue(strategy = GenerationType.TABLE)
    var id: Long = 0

    @ManyToMany(fetch = FetchType.LAZY)
    var materias: MutableSet<Materia> = HashSet()

    // Hibernate necesita un constructor sin argumentos
    // si creás un constructor con parámetros debés agregar uno sin ninguno

    fun agregarMateria(materia: Materia) {
        materias.add(materia)
    }

    override fun toString() = "$nombreCompleto ($id)"
}