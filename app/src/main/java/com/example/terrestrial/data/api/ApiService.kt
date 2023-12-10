package com.example.terrestrial.data.api

import com.example.terrestrial.data.response.AllCourseResponse
import com.example.terrestrial.data.response.DetailCourseResponse
import com.example.terrestrial.data.response.LoginResponse
import com.example.terrestrial.data.response.SignupResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("users/signup")
    suspend fun signup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignupResponse

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

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