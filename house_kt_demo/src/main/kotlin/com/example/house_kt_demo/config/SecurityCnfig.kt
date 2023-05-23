package com.example.house_kt_demo.config

import com.example.house_kt_demo.filter.JwtFilter
import com.example.house_kt_demo.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityCnfig:WebSecurityConfigurerAdapter (
){

    @Autowired
    lateinit var customUserDetailsService: CustomUserDetailsService
    @Autowired
    lateinit var jwtFilter: JwtFilter

    @Bean
    fun encoder():PasswordEncoder{
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        super.configure(auth)

            auth?.userDetailsService(customUserDetailsService)

    }

    override fun configure(http: HttpSecurity){
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/user", "/user/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}