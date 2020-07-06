package com.mkean.demo.entity

data class LoginResult (
        val statusCode: Int,
        val message: String,
        val data: TokenData

)

data class TokenData (
        val token: String
)