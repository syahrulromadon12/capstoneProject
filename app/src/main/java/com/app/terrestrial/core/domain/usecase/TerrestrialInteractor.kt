package com.app.terrestrial.core.domain.usecase

import com.app.terrestrial.core.data.source.local.entity.CourseEntity
import com.app.terrestrial.core.data.source.remote.api.ApiResponse
import com.app.terrestrial.core.data.source.remote.response.CourseResponse
import com.app.terrestrial.core.data.source.remote.response.DetailCourseResponse
import com.app.terrestrial.core.data.source.remote.response.LoginResponse
import com.app.terrestrial.core.data.source.remote.response.QuestionResponse
import com.app.terrestrial.core.data.source.remote.response.SignupResponse
import com.app.terrestrial.core.domain.model.UserModel
import com.app.terrestrial.core.domain.repository.ITerrestrialRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TerrestrialInteractor @Inject constructor(private val repository: ITerrestrialRepository) : TerrestrialUseCase {
    override suspend fun signup(name: String, email: String, password: String): Flow<ApiResponse<SignupResponse>> =
        repository.signup(name, email, password)

    override suspend fun login(email: String, password: String): Flow<ApiResponse<LoginResponse>> =
        repository.login(email, password)

    override suspend fun saveResultRecommendation(user: UserModel) =
        repository.saveResultRecommendation(user)

    override suspend fun getLoginSession(): Flow<UserModel> =
        repository.getLoginSession()

    override suspend fun saveLoginSession(user: UserModel) =
        repository.saveLoginSession(user)

    override suspend fun clearLoginSession() =
        repository.clearLoginSession()

    override suspend fun getAllCourse(): Flow<ApiResponse<CourseResponse?>> =
        repository.getAllCourse()

    override suspend fun getDetailCourse(id: String): Flow<ApiResponse<DetailCourseResponse>> =
        repository.getDetailCourse(id)

    override suspend fun getQuestion(): Flow<ApiResponse<QuestionResponse>> =
        repository.getQuestion()

    override fun getAllFavCourse(): Flow<List<CourseEntity>> =
        repository.getAllFavCourse()

    override fun insertFavCourse(courseList: CourseEntity) =
        repository.insertFavCourse(courseList)

    override fun deleteFavCourse(id: String) =
        repository.deleteFavCourse(id)

    override suspend fun isCourseBookmarked(courseId: Int): Boolean =
        repository.isCourseBookmarked(courseId)

    override suspend fun getRecommendedCourses(): Flow<ApiResponse<CourseResponse?>> =
        repository.getRecommendCourse()

    override suspend fun processAnswers(desain: Int, logika: Int, data: Int): String =
        repository.processAnswers(desain, logika, data)
}