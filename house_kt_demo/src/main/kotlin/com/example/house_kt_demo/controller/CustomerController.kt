package com.example.house_kt_demo.controller

import com.example.house_kt_demo.dto.CustomerDto
import com.example.house_kt_demo.dto.HousingDto
import com.example.house_kt_demo.entity.Customer
import com.example.house_kt_demo.service.CustomerService
import com.example.house_kt_demo.service.HousingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customer")
class CustomerController(@Autowired val customerService: CustomerService,
                         @Autowired val housingService: HousingService) {

    @GetMapping("all")
    fun findAll():List<CustomerDto>{
        return customerService.findAll()
    }

    @PostMapping("edit/{id}")
    fun edit(@PathVariable id:Int, @RequestBody dto: CustomerDto):CustomerDto{

        return customerService.edit(id, dto)
    }

    @GetMapping()
    fun helloHousing():List<HousingDto>{
        var auth = SecurityContextHolder.getContext().authentication
        return housingService.helloHousing(auth.name)
    }

    @PostMapping("create")
    fun createHousing( @RequestBody housingDto: HousingDto): HousingDto {
        var auth = SecurityContextHolder.getContext().authentication
        return housingService.createHousing(auth.name, housingDto)
    }


    @PutMapping("housing/edit/{id}")
    fun edit(
            @PathVariable id:Int, @RequestBody dto: HousingDto
    ): HousingDto {
        return housingService.edit(id, dto)
    }
}