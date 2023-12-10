package com.example.terrestrial.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.terrestrial.data.auth.UserRepository
import com.example.terrestrial.data.response.AllCourseResponse
import kotlinx.coroutines.launch
import com.example.terrestrial.data.auth.Result
import com.example.terrestrial.data.response.DataItem

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _courseList = MutableLiveData<List<DataItem>?>()
    val courseList: MutableLiveData<List<DataItem>?> get() = _courseList

    fun getAllCourse() {
        viewModelScope.launch {
            when (val result = userRepository.getAllCourse()) {
                is Result.Success -> _courseList.value
                is Result.Error -> {/* handle error */}
                else -> {}
            }
        }
    }
}
