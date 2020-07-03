package com.mkean.demo.entity

data class LoginResult(
        val token: TokenData,
        val statusCode: Int,
        val message: String
)

data class TokenData(
        val token: String
)