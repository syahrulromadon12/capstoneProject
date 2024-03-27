package com.app.terrestrial.core.data.source.remote.api

import com.app.terrestrial.core.data.source.remote.response.CourseResponse
import com.app.terrestrial.core.data.source.remote.response.DetailCourseResponse
import com.app.terrestrial.core.data.source.remote.response.LoginResponse
import com.app.terrestrial.core.data.source.remote.response.QuestionResponse
import com.app.terrestrial.core.data.source.remote.response.SignupResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    data class SignupRequest(
        val name: String,
        val email: String,
        val password: String
    )

    @POST("users/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): SignupResponse

    data class LoginRequest(
        val email: String,
        val password: String
    )

    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("course")
    suspend fun getAllCourse(
        @Header("Authorization") token: String
    ): CourseResponse

    @GET("course/{id}")
    suspend fun getDetailCourse(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): DetailCourseResponse

    @GET("question")
    suspend fun getQuestion(
        @Header("Authorization") token: String
    ) : QuestionResponse
}