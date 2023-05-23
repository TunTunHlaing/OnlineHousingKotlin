package com.example.house_kt_demo

import com.example.house_kt_demo.repo.CustomerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.password.PasswordEncoder
import javax.annotation.PostConstruct

@SpringBootApplication
class HouseKtDemoApplication

fun main(args: Array<String>) {
	runApplication<HouseKtDemoApplication>(*args)
}

@Autowired
lateinit var customerRepo:CustomerRepo
@Autowired
lateinit var encoder:PasswordEncoder

//@PostConstruct

fun init():Unit{
	var c1 = customerRepo.findCustomerByEmail("john@gmail.com")
	c1.password = encoder.encode(c1.password)
	var c2 = customerRepo.findCustomerByEmail("mary@gmail.com ")
	c2.password = encoder.encode(c2.password)
	var c3 = customerRepo.findCustomerByEmail("william@gmail.com")
	c3.password = encoder.encode(c3.password)
	customerRepo.save(c1)
	customerRepo.save(c2)
	customerRepo.save(c3)

}