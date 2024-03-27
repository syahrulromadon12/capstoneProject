package com.app.terrestrial.core.data.source.remote

import com.app.terrestrial.core.data.source.local.sharedPreferences.UserPreferences
import com.app.terrestrial.core.data.source.remote.api.ApiResponse
import com.app.terrestrial.core.data.source.remote.api.ApiService
import com.app.terrestrial.core.data.source.remote.response.CourseResponse
import com.app.terrestrial.core.data.source.remote.response.DetailCourseResponse
import com.app.terrestrial.core.data.source.remote.response.LoginResponse
import com.app.terrestrial.core.data.source.remote.response.QuestionResponse
import com.app.terrestrial.core.data.source.remote.response.SignupResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    private suspend fun getToken(): String {
        return userPreferences.getLoginSession().firstOrNull()?.token ?: ""
    }

    suspend fun signup(name: String, email: String, password: String): Flow<ApiResponse<SignupResponse>> {
        val request = ApiService.SignupRequest(name, email, password)
        return flow {
            try {
                val response = apiService.signup(request)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun login(email: String, password: String): Flow<ApiResponse<LoginResponse>> {
        val request = ApiService.LoginRequest(email, password)
        return flow {
            try {
                val response = apiService.login(request)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun getAllCourse(): Flow<ApiResponse<CourseResponse>> {
        return flow {
            try {
                val response = apiService.getAllCourse(getToken())
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun getRecommendedCourses(): Flow<ApiResponse<CourseResponse>> {
        return flow {
            try {
                val response = apiService.getAllCourse(getToken())
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun getDetailCourse(id: String): Flow<ApiResponse<DetailCourseResponse>> {
        return flow {
            try {
                val response = apiService.getDetailCourse(getToken(), id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun getQuestion(): Flow<ApiResponse<QuestionResponse>> {
        return flow {
            try {
                val response = apiService.getQuestion(getToken())
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }
}
