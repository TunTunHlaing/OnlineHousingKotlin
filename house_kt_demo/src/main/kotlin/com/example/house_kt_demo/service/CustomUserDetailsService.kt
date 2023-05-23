package com.example.house_kt_demo.service

import com.example.house_kt_demo.repo.CustomerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService: UserDetailsService {

    @Autowired
    lateinit var repo:CustomerRepo
    override fun loadUserByUsername(username: String?): UserDetails {

       var customer = username?.let { repo.findCustomerByEmail(it) }

        return User(customer?.email, customer?.password, arrayListOf())
    }
}