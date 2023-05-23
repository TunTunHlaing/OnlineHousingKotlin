package com.example.house_kt_demo.exception

import org.springframework.validation.FieldError
import java.util.function.Function
import java.util.stream.Collectors


class ValidationException(private val errors: List<FieldError>) : RuntimeException() {

    val messages: List<String?>
        get() = errors.map { it.defaultMessage }

    companion object {
        private const val serialVersionUID: Long = 1L
    }
}