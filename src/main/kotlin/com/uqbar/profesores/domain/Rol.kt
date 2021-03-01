package com.uqbar.profesores.domain

import javax.persistence.*

@Entity
class Rol(@Column var name: String? = null, @Column var description: String? = null) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0
}