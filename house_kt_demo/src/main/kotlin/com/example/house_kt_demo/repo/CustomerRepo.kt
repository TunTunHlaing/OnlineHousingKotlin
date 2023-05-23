package com.example.house_kt_demo.repo

import com.example.house_kt_demo.entity.Customer
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.data.repository.PagingAndSortingRepository

interface CustomerRepo:JpaRepositoryImplementation<Customer, Int>, PagingAndSortingRepository<Customer, Int> {

    fun findCustomerByEmail(email:String):Customer
}