package com.example.house_kt_demo.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import java.util.Objects

@Service
class Jwt {

    private val secretKey:String = "tuntunhlaing"


    fun extractUserName(token: String):String{
        return extractClaim(token, Claims::getSubject)
    }

    fun extractExpiration(token: String):Date{
        return extractClaim(token, Claims::getExpiration)
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims: Claims = extractALlClaim(token)
        return claimsResolver(claims)
    }


    fun extractALlClaim(token:String):Claims{
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body;
    }

    fun isTokenExpired(token: String):Boolean{
        return extractExpiration(token).before(Date())
    }

    fun generateToken(username:String):String{
        var claims = HashMap<String, Objects>()
        return createToken(claims, username)
    }

    fun validToken(token:String, userDetails:UserDetails):Boolean{
        val username = extractUserName(token)
        return (username.equals(userDetails.username) && !isTokenExpired(token))
    }

    fun createToken(claims:Map<String, Objects>, subject:String):String{

        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 *10))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact()
    }
}