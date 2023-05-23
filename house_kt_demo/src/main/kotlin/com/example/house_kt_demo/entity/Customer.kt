package com.example.house_kt_demo.entity

import com.example.house_kt_demo.dto.CustomerDto
import com.example.house_kt_demo.dto.HousingDto
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Entity
data class Customer(

        @NotBlank(message = "No Blank")
        @javax.validation.constraints.NotNull(message = "Not Null")
        @NotEmpty(message = "Not Empty")
        var name:String,

        @Column(unique = true)
        var email:String,

        var password:String,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        var createdDate: LocalDate,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        var updatedDate: LocalDate
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int = 0

    @OneToMany(mappedBy = "customer", cascade = arrayOf(CascadeType.PERSIST))
    @JsonIgnore
    var housingList:MutableList<Housing> = mutableListOf()


    fun addHousing(housing:Housing):Unit{
        housing.customer = this
        housingList.add(housing)

    }


}