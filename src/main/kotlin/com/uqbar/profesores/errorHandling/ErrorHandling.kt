package com.uqbar.profesores.errorHandling

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UserException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(msg: String) : RuntimeException(msg)