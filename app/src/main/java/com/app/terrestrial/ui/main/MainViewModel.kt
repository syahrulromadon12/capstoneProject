package com.app.terrestrial.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.terrestrial.data.auth.UserRepository
import com.app.terrestrial.data.auth.UserModel

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> = userRepository.getLoginSession().asLiveData()

}
