package com.example.terrestrial.data.auth

data class UserModel(
    val name: String,
    val email: String,
    val token: String,
    val isLogin: Boolean = false,
    val isAnswer: Boolean = false
)