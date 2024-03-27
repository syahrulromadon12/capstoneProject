package com.app.terrestrial.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.terrestrial.core.data.source.local.entity.CourseEntity
import com.app.terrestrial.core.domain.usecase.TerrestrialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val terrestrialUseCase: TerrestrialUseCase) : ViewModel() {

    private val _favoriteCourses = MutableLiveData<List<CourseEntity>>()
    val favoriteCourses: LiveData<List<CourseEntity>> = _favoriteCourses

    fun fetchAllFavoriteCourses() {
        viewModelScope.launch {
            terrestrialUseCase.getAllFavCourse().collect { courses ->
                _favoriteCourses.value = courses
            }
        }
    }
}