package com.example.house_kt_demo.service

import com.example.house_kt_demo.dto.CustomerDto
import com.example.house_kt_demo.entity.Customer
import com.example.house_kt_demo.exception.UserNotExistException
import com.example.house_kt_demo.exception.ValidException
import com.example.house_kt_demo.repo.CustomerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.stream.Collectors
import kotlin.jvm.Throws
import kotlin.streams.toList

@Service
class CustomerService(@Autowired private val repo:CustomerRepo,
        @Autowired private val encoder:PasswordEncoder) {

    fun findAll():List<CustomerDto>{
        var customerList = repo.findAll();
        var dtoList =
        return customerList.stream().map { toDto(it) }.collect(Collectors.toList())
    }


    fun createCustomer(customerDto: CustomerDto): CustomerDto {

        repo.save(toentity(customerDto))
        return customerDto
    }

    fun toDto(customer: Customer):CustomerDto{
        return CustomerDto( customer.name,customer.email,customer.password,customer.createdDate.toString(),customer.updatedDate.toString())
    }

    fun toentity(customerDto:CustomerDto):Customer {

        return Customer(customerDto.name, customerDto.email, encoder.encode(customerDto.password), LocalDate.parse(customerDto.createdDate), LocalDate.parse(customerDto.updatedDate))
    }

    fun edit(id: Int, dto: CustomerDto): CustomerDto {
        var existingCustomer = repo.findById(id).orElseThrow{UserNotExistException()}
        existingCustomer.name = dto.name
        existingCustomer.email = dto.email
        existingCustomer.password = encoder.encode(dto.password)
        existingCustomer.createdDate = LocalDate.parse(dto.createdDate)
        existingCustomer.updatedDate = LocalDate.parse(dto.updatedDate)
        repo.save(existingCustomer)
        return dto

    }
}