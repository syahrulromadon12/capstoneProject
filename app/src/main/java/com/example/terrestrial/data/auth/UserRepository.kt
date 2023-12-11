package com.example.terrestrial.data.auth

import com.example.terrestrial.data.api.ApiService
import com.example.terrestrial.data.response.AllCourseResponse
import com.example.terrestrial.data.response.LoginResponse
import com.example.terrestrial.data.response.SignupResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    suspend fun signup(request: ApiService.SignupRequest): Result<SignupResponse?> {
        return try {
            val response = apiService.signup(request)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }

    suspend fun login(request: ApiService.LoginRequest): LoginResponse {
        return apiService.login(request)
    }



    suspend fun saveLoginSession(user: UserModel) {
        userPreferences.saveLoginSession(user)
    }

    fun getLoginSession(): Flow<UserModel> = userPreferences.getLoginSession()

    suspend fun clearLoginSession() {
        userPreferences.clearLoginSession()
    }

    suspend fun getAllCourse(): Result<List<AllCourseResponse>?> {
        return try {
            val response = apiService.getAllCourse()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreferences: UserPreferences): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreferences)
            }.also { instance = it }
    }
}
