package com.app.terrestrial.core.data.source.local

import com.app.terrestrial.core.data.source.local.entity.CourseEntity
import com.app.terrestrial.core.data.source.local.room.CourseFavDao
import com.app.terrestrial.core.data.source.local.sharedPreferences.UserPreferences
import com.app.terrestrial.core.domain.model.UserModel

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val courseFavDao: CourseFavDao,
    private val userPreferences: UserPreferences
) {

    fun getAllFavCourse(): Flow<List<CourseEntity>> = courseFavDao.getAllFavCourse()

    fun insertFavCourse(course: CourseEntity) = courseFavDao.insert(course)

    fun deleteFavCourse(id: String) = courseFavDao.delete(id)

    suspend fun saveLoginSession(user: UserModel) = userPreferences.saveLoginSession(user)

    suspend fun saveResultRecommendation(user: UserModel) = userPreferences.saveResultRecommendation(user)

    fun getLoginSession(): Flow<UserModel> = userPreferences.getLoginSession()

    suspend fun clearLoginSession() = userPreferences.clearLoginSession()

    fun isCourseBookmarked(courseId: Int): Boolean {
        return courseFavDao.getCourseById(courseId) != null
    }
}
