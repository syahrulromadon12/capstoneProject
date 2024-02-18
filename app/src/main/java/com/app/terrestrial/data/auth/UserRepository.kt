package com.app.terrestrial.data.auth

import android.util.Log
import com.app.terrestrial.data.api.ApiConfig
import com.app.terrestrial.data.api.ApiService
import com.app.terrestrial.data.response.AllCourseResponse
import com.app.terrestrial.data.response.DetailCourseResponse
import com.app.terrestrial.data.response.LoginResponse
import com.app.terrestrial.data.response.QuestionResponse
import com.app.terrestrial.data.response.SignupResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    suspend fun signup(request: ApiService.SignupRequest): SignupResponse {
        return apiService.signup(request)
    }

    suspend fun login(request: ApiService.LoginRequest): LoginResponse {
        return apiService.login(request)
    }

    suspend fun saveLoginSession(user: UserModel) {
        userPreferences.saveLoginSession(user)
    }

    suspend fun saveResultRecommendation(user: UserModel) {
        userPreferences.saveResultRecommendation(user)
    }

    fun getLoginSession(): Flow<UserModel> = userPreferences.getLoginSession()

    suspend fun clearLoginSession() {
        userPreferences.clearLoginSession()

    }

    suspend fun getAllCourse(): Result<AllCourseResponse?> {
        return try {
            val user = userPreferences.getLoginSession().first()
            val apiService = ApiConfig.getApiService(user.token)
            val response = apiService.getAllCourse()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }


    suspend fun getRecommendCourse(): Result<AllCourseResponse?> {
        return try {
            val user = userPreferences.getLoginSession().first()
            val apiService = ApiConfig.getApiService(user.token)

            val recommendClass = user.resultRecommendation
            val response = apiService.getRecommendCourse(recommendClass)

            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }

    suspend fun getDetailCourse(id: String): Result<DetailCourseResponse?> {
        return try {
            val user = userPreferences.getLoginSession().first()
            val apiService = ApiConfig.getApiService(user.token)
            val response = apiService.getDetailCourse(id)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }

    suspend fun getLoginSessionFromDataStore(): UserModel {
        return userPreferences.getLoginSession().first()
    }


    suspend fun getQuestion(): Result<QuestionResponse>{
        return try {
            val user = userPreferences.getLoginSession().first()
            val apiService = ApiConfig.getApiService(user.token)
            Log.e("getQuestion", apiService.toString())
            val response = apiService.getQuestion()
            Result.Success(response)
        }catch (e: Exception){
            Result.Error(e.message.toString())
        }
    }

//    suspend fun submitAnswers(desain: Int, logika: Int, data: Int): PredictResponse {
//        val answerRequest = ApiService.AnswerRequest(desain, logika, data)
//        return apiService.submitAnswers(answerRequest)
//    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreferences: UserPreferences): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreferences)
            }.also { instance = it }
    }
}
