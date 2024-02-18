package com.app.terrestrial.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.terrestrial.data.auth.UserRepository
import com.app.terrestrial.ui.detail.DetailCourseViewModel
import com.app.terrestrial.ui.home.HomeViewModel
import com.app.terrestrial.ui.login.LoginViewModel
import com.app.terrestrial.utils.Injection
import com.app.terrestrial.ui.main.MainViewModel
import com.app.terrestrial.ui.recommendation.QuestionViewModel
import com.app.terrestrial.ui.setting.SettingViewModel
import com.app.terrestrial.ui.signup.SignupViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel() as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailCourseViewModel::class.java) -> {
                DetailCourseViewModel(repository) as T
            }
            modelClass.isAssignableFrom(QuestionViewModel::class.java) -> {
                QuestionViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory{
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepository(context),
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}