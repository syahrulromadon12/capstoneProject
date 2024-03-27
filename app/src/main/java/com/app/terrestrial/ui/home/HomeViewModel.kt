package com.app.terrestrial.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.app.terrestrial.core.data.source.remote.api.ApiResponse
import com.app.terrestrial.core.data.source.remote.response.DataItem
import com.app.terrestrial.core.domain.model.UserModel
import com.app.terrestrial.core.domain.usecase.TerrestrialUseCase
import com.app.terrestrial.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val terrestrialUseCase: TerrestrialUseCase) : ViewModel() {

    private val _courseList = MutableLiveData<List<DataItem>>()
    val courseList: LiveData<List<DataItem>> get() = _courseList

    private val _recommendCourseList = MutableLiveData<List<DataItem>>()
    val recommendCourseList: LiveData<List<DataItem>> get() = _recommendCourseList

    private val _profile = MutableLiveData<String>().apply {
        val drawableResourceId = R.drawable.default_profile
        value = drawableResourceId.toString()
    }
    val profile: LiveData<String> = _profile

    suspend fun getSession(): LiveData<UserModel> {
        return terrestrialUseCase.getLoginSession().asLiveData()
    }

    fun getAllCourse() {
        viewModelScope.launch {
            terrestrialUseCase.getAllCourse().collect { result ->
                when (result) {
                    is ApiResponse.Success -> {
                        val course = result.data?.data ?: emptyList()
                        _courseList.value = course
                    }
                    is ApiResponse.Error -> {
                        Log.e("HomeViewModel", "Error getting all courses: ${result.errorMessage}")
                    }
                    else -> {}
                }
            }
        }
    }

    fun getRecommendCourse() {
        viewModelScope.launch {
            val user = terrestrialUseCase.getLoginSession().first()

            terrestrialUseCase.getRecommendedCourses().collect { result ->
                when (result) {
                    is ApiResponse.Success -> {
                        val allCourses = result.data?.data ?: emptyList()
                        val groupedCourses = allCourses.groupBy { it.courseType }

                        val recommendCourses = groupedCourses[user.resultRecommendation]?.toList() ?: emptyList()

                        _recommendCourseList.value = recommendCourses
                    }
                    is ApiResponse.Error -> {
                        Log.e("HomeViewModel", "Error getting all courses: ${result.errorMessage}")
                    }
                    else -> {}
                }
            }
        }
    }
}