package com.example.house_kt_demo.dto

import lombok.NoArgsConstructor
import org.jetbrains.annotations.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@NoArgsConstructor
data class CustomerDto (

        @field:NotBlank(message = "Name can't be null")
        @field:javax.validation.constraints.NotNull(message = "Name can't be null")
        @field:NotEmpty(message = "Not Empty")
        var name:String,

        @Column(unique = true)
        var email:String,

        var password:String,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        var createdDate: String,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        var updatedDate: String
){

}