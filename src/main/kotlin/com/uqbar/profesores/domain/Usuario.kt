package com.uqbar.profesores.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.persistence.*

@Entity
class Usuario(@Column var username: String? = null, @Column @JsonIgnore var password: String? = null,
              @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
              @JoinTable(name = "USER_ROLES", joinColumns = [JoinColumn(name = "USER_ID")], inverseJoinColumns = [JoinColumn(name = "ROLE_ID")])
              var roles: Set<Rol>? = null) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}