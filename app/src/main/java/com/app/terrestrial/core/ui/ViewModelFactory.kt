package com.app.terrestrial.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.terrestrial.core.domain.usecase.TerrestrialUseCase
import com.app.terrestrial.ui.detail.DetailCourseViewModel
import com.app.terrestrial.ui.favorite.FavoriteViewModel
import com.app.terrestrial.ui.home.HomeViewModel
import com.app.terrestrial.ui.login.LoginViewModel
import com.app.terrestrial.ui.main.MainViewModel
import com.app.terrestrial.ui.recommendation.QuestionViewModel
import com.app.terrestrial.ui.setting.SettingViewModel
import com.app.terrestrial.ui.signup.SignupViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val terrestrialUseCase: TerrestrialUseCase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(terrestrialUseCase) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(terrestrialUseCase) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(terrestrialUseCase) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(terrestrialUseCase) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(terrestrialUseCase) as T
            }
            modelClass.isAssignableFrom(DetailCourseViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return DetailCourseViewModel(terrestrialUseCase) as T
            }
            modelClass.isAssignableFrom(QuestionViewModel::class.java) -> {
                QuestionViewModel(terrestrialUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(terrestrialUseCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}