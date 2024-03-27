package com.app.terrestrial.core.data

import com.app.terrestrial.core.data.source.local.LocalDataSource
import com.app.terrestrial.core.data.source.local.entity.CourseEntity
import com.app.terrestrial.core.data.source.remote.RemoteDataSource
import com.app.terrestrial.core.data.source.remote.api.ApiResponse
import com.app.terrestrial.core.data.source.remote.response.CourseResponse
import com.app.terrestrial.core.data.source.remote.response.DetailCourseResponse
import com.app.terrestrial.core.data.source.remote.response.LoginResponse
import com.app.terrestrial.core.data.source.remote.response.QuestionResponse
import com.app.terrestrial.core.data.source.remote.response.SignupResponse
import com.app.terrestrial.core.domain.model.UserModel
import com.app.terrestrial.core.domain.repository.ITerrestrialRepository
import com.app.terrestrial.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TerrestrialRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ITerrestrialRepository {

    override suspend fun signup(name: String, email: String, password: String): Flow<ApiResponse<SignupResponse>> {
        return remoteDataSource.signup(name, email, password)
    }

    override suspend fun login(email: String, password: String): Flow<ApiResponse<LoginResponse>> {
        return remoteDataSource.login(email, password)
    }

    override suspend fun saveLoginSession(user: UserModel) {
        localDataSource.saveLoginSession(user)
    }

    override suspend fun saveResultRecommendation(user: UserModel) {
        localDataSource.saveResultRecommendation(user)
    }

    override fun getAllFavCourse(): Flow<List<CourseEntity>> {
        return localDataSource.getAllFavCourse()
    }

    override fun insertFavCourse(courseList: CourseEntity) {
        localDataSource.insertFavCourse(courseList)
    }

    override fun deleteFavCourse(id: String) {
        localDataSource.deleteFavCourse(id)
    }

    override suspend fun getLoginSession(): Flow<UserModel> {
        return localDataSource.getLoginSession()
    }

    override suspend fun getRecommendCourse(): Flow<ApiResponse<CourseResponse?>> {
        return remoteDataSource.getRecommendedCourses()
    }

    override suspend fun clearLoginSession() {
        localDataSource.clearLoginSession()
    }

    override suspend fun getAllCourse(): Flow<ApiResponse<CourseResponse?>> {
        return remoteDataSource.getAllCourse()
    }

    override suspend fun getDetailCourse(id: String): Flow<ApiResponse<DetailCourseResponse>> {
        return remoteDataSource.getDetailCourse(id)
    }


    override suspend fun getQuestion(): Flow<ApiResponse<QuestionResponse>> {
        return remoteDataSource.getQuestion()
    }

    override suspend fun isCourseBookmarked(courseId: Int): Boolean {
        return localDataSource.isCourseBookmarked(courseId)
    }

    override suspend fun processAnswers(desain: Int, logika: Int, data: Int): String {
        val recommendations = listOf(
            Triple(7, 0, 0), Triple(6, 1, 0), Triple(6, 0, 1), Triple(5, 1, 1),
            Triple(4, 2, 1), Triple(4, 1, 2), Triple(4, 3, 0), Triple(4, 0, 3),
            Triple(3, 3, 1), Triple(3, 1, 3), Triple(3, 2, 2)
        )

        val result = when {
            recommendations.any { (d, l, da) -> d == desain && l == logika && da == data } -> "Frontend"
            recommendations.any { (l, d, da) -> l == logika && d == desain && da == data } -> "Backend"
            else -> "Data"
        }

        saveResultRecommendation(UserModel(isAnswer = true, resultRecommendation = result))

        return result
    }
}