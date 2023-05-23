package com.example.house_kt_demo.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Housing(

        @JsonProperty("housing_name")
        var housingName:String,

        @JsonProperty("address")
        var address:String,

        @JsonProperty("number_of_floors")
        var numberOfFloors: Int,

        @JsonProperty("amount")
        var amount:Int,

        @JsonProperty("created_date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        var createDate: LocalDate,

        @JsonProperty("updated_date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        var updatedDate: LocalDate
) {

         @Id
         @GeneratedValue(strategy = GenerationType.IDENTITY)
         var id:Int = 0

         @ManyToOne
         lateinit var  customer:Customer
}