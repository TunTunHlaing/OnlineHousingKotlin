package com.example.house_kt_demo.controller

import com.example.house_kt_demo.dto.AuthenticateRequest
import com.example.house_kt_demo.dto.CustomerDto
import com.example.house_kt_demo.dto.HousingDto
import com.example.house_kt_demo.exception.ValidException
import com.example.house_kt_demo.exception.ValidationException
import com.example.house_kt_demo.service.CustomerService
import com.example.house_kt_demo.service.HousingService
import com.example.house_kt_demo.service.Jwt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.jvm.Throws

@RestController
@RequestMapping("user")
class UserController (@Autowired val housingService: HousingService){

    @Autowired
    lateinit var jwt:Jwt

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var customerService:CustomerService

    @GetMapping("all")
    fun allHousing(@RequestParam(defaultValue = "0") page:Int,
                   @RequestParam(defaultValue = "5") pageSize:Int):List<HousingDto>{
        return housingService.findAll(page, pageSize)
    }

    @GetMapping("search")
    fun dynamicSearch(
            @RequestParam(defaultValue = "0") page:Int,
            @RequestParam(defaultValue = "5") size:Int,
            @RequestParam("name") housingName: Optional<String>,
            @RequestParam("floors") numberOfFloors: Optional<Int>,
            @RequestParam amount: Optional<Int>
    ):List<HousingDto>{
        return housingService.dynamicSearch(page, size, housingName, numberOfFloors, amount)
    }

    @Throws(ValidationException::class)
    @PostMapping("create", consumes = [ MediaType.APPLICATION_FORM_URLENCODED_VALUE ])
    fun createCustomer(@Validated customerDto: CustomerDto, result:BindingResult): CustomerDto {

        try {
            return customerService.createCustomer(customerDto)
        }catch (e : DataIntegrityViolationException){
            throw ValidException()
        }
    }

    @PostMapping("authenticate")
    fun authenticate(@RequestBody authenticateRequest: AuthenticateRequest):String{
        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(authenticateRequest.username, authenticateRequest.password)
        )
        return jwt.generateToken(authenticateRequest.username)
    }

}