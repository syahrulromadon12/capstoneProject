package com.app.terrestrial.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.terrestrial.R
import com.app.terrestrial.data.auth.UserRepository
import kotlinx.coroutines.launch
import com.app.terrestrial.data.auth.Result
import com.app.terrestrial.data.auth.UserModel
import com.app.terrestrial.data.auth.UserPreferences
import com.app.terrestrial.data.response.DataItem

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val userPreferences = UserPreferences

    private val _courseList = MutableLiveData<List<DataItem>?>()
    val courseList: MutableLiveData<List<DataItem>?> get() = _courseList

    private val _recommendCourseList = MutableLiveData<List<DataItem?>>()
    val recommendCourseList: MutableLiveData<List<DataItem?>> get() = _recommendCourseList

    private val _profile = MutableLiveData<String>().apply {
        val drawableResourceId = R.drawable.default_profile
        value = drawableResourceId.toString()
    }
    val profile: LiveData<String> = _profile

//    private val _searchedCourseList = MutableLiveData<List<DataItem?>?>()
//    val searchedCourseList: LiveData<List<DataItem?>?> get() = _searchedCourseList


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
            // Dapatkan data user dari preferensi
            val user = userRepository.getLoginSessionFromDataStore()

            when (val result = userRepository.getAllCourse()) {
                is Result.Success -> {
                    val allCourses = result.data?.data ?: emptyList()
                    val groupedCourses = allCourses.groupBy { it?.courseType }

                    // Filter kursus berdasarkan preferensi pengguna
                    val recommendCourses = groupedCourses[user.resultRecommendation]?.toList() ?: emptyList()

                    _recommendCourseList.value = recommendCourses
                }
                is Result.Error -> {
                    // Handle error
                }
                else -> {}
            }
        }
    }

//    fun searchCourse(query: String) {
//        viewModelScope.launch {
//            try {
//                val response = userRepository.searchCourse(query)
//                if (!response.error!!) {
//                    _searchedCourseList.postValue(response.data)
//                } else {
//                    // Handle error jika diperlukan
//                }
//            } catch (e: Exception) {
//                // Handle exception jika diperlukan
//            }
//        }
//    }
}

