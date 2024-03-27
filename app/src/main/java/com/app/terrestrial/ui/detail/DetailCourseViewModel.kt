package com.app.terrestrial.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.terrestrial.core.data.Result
import com.app.terrestrial.core.data.source.local.entity.CourseEntity
import com.app.terrestrial.core.data.source.remote.api.ApiResponse
import com.app.terrestrial.core.data.source.remote.response.DetailCourseResponse
import com.app.terrestrial.core.domain.usecase.TerrestrialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailCourseViewModel @Inject constructor(private val terrestrialUseCase: TerrestrialUseCase) : ViewModel() {
    private val _detailCourse = MutableStateFlow<com.app.terrestrial.core.data.Result<DetailCourseResponse>>(
        com.app.terrestrial.core.data.Result.Loading)
    val detailCourse: StateFlow<com.app.terrestrial.core.data.Result<DetailCourseResponse?>> = _detailCourse.asStateFlow()

    suspend fun isCourseBookmarked(courseId: Int?): Boolean {
        return withContext(Dispatchers.IO) {
            courseId?.let {
                terrestrialUseCase.isCourseBookmarked(it)
            } ?: false
        }
    }

    fun getDetail(id: String) {
        viewModelScope.launch {
            terrestrialUseCase.getDetailCourse(id).collect { response ->
                when (response) {
                    is ApiResponse.Success -> _detailCourse.value = com.app.terrestrial.core.data.Result.Success(response.data)
                    is ApiResponse.Error -> _detailCourse.value = com.app.terrestrial.core.data.Result.Error("Error occurred: ${response.errorMessage}")
                    ApiResponse.Empty -> {}
                }
            }
        }
    }

    fun addToBookmark(course: CourseEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                terrestrialUseCase.insertFavCourse(course)
            }
        }
    }

    fun removeFromBookmark(courseId: Int?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                terrestrialUseCase.deleteFavCourse(courseId.toString())
            }
        }
    }
}
