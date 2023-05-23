package com.example.house_kt_demo.exceptionHandler

import com.example.house_kt_demo.dto.Message
import com.example.house_kt_demo.exception.UserNotExistException
import com.example.house_kt_demo.exception.ValidException
import com.example.house_kt_demo.exception.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@ControllerAdvice
@RestControllerAdvice
class ExceptionController {

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(ValidationException::class)
    fun handle(e: ValidationException): Message? {
        return Message(Message.Type.Error, listOf(e.messages.toString()))
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotExistException::class)
    fun handleUserNotFoundException(e: UserNotExistException): ResponseEntity<String>? {
        return ResponseEntity
                .badRequest()
                .body("User not existed! or Housing Id is wrong")
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(ValidException::class)
    fun handleValid(e: ValidException): ResponseEntity<String>? {
        return ResponseEntity
                .badRequest()
                .body("Email already existed!")
    }
}