package com.app.terrestrial.core.domain.repository

import com.app.terrestrial.core.data.source.local.entity.CourseEntity
import com.app.terrestrial.core.data.source.remote.api.ApiResponse
import com.app.terrestrial.core.data.source.remote.response.CourseResponse
import com.app.terrestrial.core.data.source.remote.response.DetailCourseResponse
import com.app.terrestrial.core.data.source.remote.response.LoginResponse
import com.app.terrestrial.core.data.source.remote.response.QuestionResponse
import com.app.terrestrial.core.data.source.remote.response.SignupResponse
import com.app.terrestrial.core.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface ITerrestrialRepository {
    suspend fun signup(name: String, email: String, password: String): Flow<ApiResponse<SignupResponse>>
    suspend fun login(email: String, password: String): Flow<ApiResponse<LoginResponse>>
    suspend fun getAllCourse(): Flow<ApiResponse<CourseResponse?>>
    suspend fun getDetailCourse(id: String): Flow<ApiResponse<DetailCourseResponse>>
    suspend fun getQuestion(): Flow<ApiResponse<QuestionResponse>>
    fun getAllFavCourse(): Flow<List<CourseEntity>>
    fun insertFavCourse(courseList: CourseEntity)
    fun deleteFavCourse(id: String)
    suspend fun saveLoginSession(user: UserModel)
    suspend fun saveResultRecommendation(user: UserModel)
    suspend fun getLoginSession(): Flow<UserModel>
    suspend fun clearLoginSession()
    suspend fun getRecommendCourse(): Flow<ApiResponse<CourseResponse?>>
    suspend fun isCourseBookmarked(courseId: Int): Boolean
    suspend fun processAnswers(desain: Int, logika: Int, data: Int): String
}