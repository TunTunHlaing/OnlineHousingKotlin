package com.example.house_kt_demo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ValidException:ResponseStatusException(HttpStatus.BAD_REQUEST, "Check ur fields ") {
}