package com.example.terrestrial.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.terrestrial.data.auth.UserRepository
import kotlinx.coroutines.launch
import com.example.terrestrial.data.auth.Result
import com.example.terrestrial.data.auth.UserModel
import com.example.terrestrial.data.response.DataItem

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _courseList = MutableLiveData<List<DataItem>?>()
    val courseList: MutableLiveData<List<DataItem>?> get() = _courseList

    private val _recommendCourseList = MutableLiveData<List<DataItem>?>()
    val recommendCourseList: MutableLiveData<List<DataItem>?> get() = _recommendCourseList

    fun getSession(): LiveData<UserModel> = userRepository.getLoginSession().asLiveData()

    fun getAllCourse() {
        viewModelScope.launch {
            when (val result = userRepository.getAllCourse()) {
                is Result.Success -> _courseList.value = result.data?.data as List<DataItem>?
                is Result.Error -> {/* handle error */}
                else -> {}
            }
        }
    }

    fun getRecommendCourse() {
        viewModelScope.launch {
            when (val result = userRepository.getRecommendCourse()) {
                is Result.Success -> _recommendCourseList.value = result.data?.data as List<DataItem>?
                is Result.Error -> {/* handle error */}
                else -> {}
            }
        }
    }
}

