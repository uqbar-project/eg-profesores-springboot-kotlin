package com.uqbar.profesores.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.HashSet
import javax.persistence.*

@Entity
class Usuario(@Column var username: String? = null, @Column @JsonIgnore var password: String? = null) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    constructor() : this("", "")

    @ManyToMany(fetch = FetchType.LAZY)
    var roles: MutableSet<Rol> = HashSet()

    fun agregarRol(rol: Rol) {
        roles.add(rol)
    }
}