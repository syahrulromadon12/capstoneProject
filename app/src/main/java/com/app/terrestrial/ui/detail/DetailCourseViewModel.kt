package com.app.terrestrial.ui.detail

import androidx.lifecycle.ViewModel
import com.app.terrestrial.data.auth.Result
import com.app.terrestrial.data.auth.UserRepository
import com.app.terrestrial.data.response.DetailCourseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailCourseViewModel(private val repository: UserRepository) : ViewModel() {
    private val _detailCourse = MutableStateFlow<Result<DetailCourseResponse?>>(Result.Loading)
    val detailCourse: StateFlow<Result<DetailCourseResponse?>> = _detailCourse.asStateFlow()

    suspend fun getDetail(id: String) {
        _detailCourse.value = repository.getDetailCourse(id)
    }
}

