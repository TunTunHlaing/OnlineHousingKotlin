package com.example.house_kt_demo.dto

import com.example.house_kt_demo.entity.Customer
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat

data class HousingDto (

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
        var createDate: String,

        @JsonProperty("updated_date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        var updatedDate: String

){

        var customerDto: CustomerDto? = null

}