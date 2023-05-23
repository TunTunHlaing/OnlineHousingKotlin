package com.example.house_kt_demo.filter

import com.example.house_kt_demo.service.CustomUserDetailsService
import com.example.house_kt_demo.service.Jwt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter:OncePerRequestFilter() {

    @Autowired
    lateinit var customUserDetailsService: CustomUserDetailsService
    @Autowired
    lateinit var jwt: Jwt

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

       var authorizationHeader = request.getHeader("Authorization")
        var token: String? = null
        var username: String? = null

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7)
            username = jwt.extractUserName(token)

            if (username !=null && SecurityContextHolder.getContext().authentication == null){
                var userDetails = customUserDetailsService.loadUserByUsername(username)

                if (jwt.validToken(token, userDetails)){
                    val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                }
            }
        }

        filterChain.doFilter(request, response)

    }
}


