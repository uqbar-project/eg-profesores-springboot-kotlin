package com.uqbar.profesores.domain

import javax.persistence.*

@Entity
class Materia(@Column var nombre: String = "", @Column var anio: Int = 0) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    // Hibernate necesita un constructor sin argumentos
    // si creás un constructor con parámetros debés agregar uno sin ninguno

    override fun toString() = "$nombre ($anio)"
}