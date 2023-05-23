package com.example.house_kt_demo.aspect

import com.example.house_kt_demo.exception.ValidationException
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult


@Aspect
@Component
class ValidationAspect {

    @Before(value = "@within(org.springframework.web.bind.annotation.RestController) && args(..,result)", argNames = "result")
    fun check(result: BindingResult) {
        if (result.hasErrors()) {
            throw ValidationException(result.fieldErrors)
        }
    }
}