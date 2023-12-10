package com.example.terrestrial.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.terrestrial.data.auth.UserRepository
import com.example.terrestrial.data.auth.UserModel
import com.example.terrestrial.data.response.LoginResponse
import com.example.terrestrial.data.response.SignupResponse
import com.example.terrestrial.data.auth.Result
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun signup(name: String, email: String, password: String): LiveData<Result<SignupResponse?>> =
        liveData(viewModelScope.coroutineContext) {
            emit(Result.Loading)
            try {
                when (val response = userRepository.signup(name, email, password)) {
                    is Result.Success -> emit(Result.Success(response.data))
                    is Result.Error -> emit(Result.Error(response.error))
                    else -> {}
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun login(email: String, password: String): LiveData<Result<LoginResponse?>> =
        liveData(viewModelScope.coroutineContext) {
            emit(Result.Loading)
            try {
                when (val response = userRepository.login(email, password)) {
                    is Result.Success -> emit(Result.Success(response.data))
                    is Result.Error -> emit(Result.Error(response.error))
                    else -> {}
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getSession(): LiveData<UserModel> = userRepository.getLoginSession().asLiveData()

    fun setLogin(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveLoginSession(user)
        }
    }
}
