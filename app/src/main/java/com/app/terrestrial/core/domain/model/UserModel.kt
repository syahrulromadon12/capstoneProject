package com.app.terrestrial.core.domain.model

data class UserModel(
    val email: String = null.toString(),
    val name: String = "",
    val token: String = "",
    val isLogin: Boolean = false,
    val isAnswer: Boolean = false,
    val resultRecommendation: String = ""
)