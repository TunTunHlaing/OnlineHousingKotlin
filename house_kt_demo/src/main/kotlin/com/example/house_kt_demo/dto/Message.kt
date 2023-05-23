package com.example.house_kt_demo.dto

data class Message(
        val type: Type,
        val messages: List<String>
) {
    enum class Type {
        Information, Alert, Error
    }
}
