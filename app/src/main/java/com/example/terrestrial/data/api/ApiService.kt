package com.example.terrestrial.data.api

import com.example.terrestrial.data.auth.UserRepository
import com.example.terrestrial.data.response.AllCourseResponse
import com.example.terrestrial.data.response.DetailCourseResponse
import com.example.terrestrial.data.response.LoginResponse
import com.example.terrestrial.data.response.SignupResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
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
    suspend fun login(@Body request: LoginRequest): LoginResponse


    @FormUrlEncoded
    @GET("course")
    suspend fun getAllCourse(
    ): List<AllCourseResponse>

    @FormUrlEncoded
    @GET("course/{id}")
    suspend fun getDetailCourse(
        @Path("id") id: String
    ): DetailCourseResponse
}