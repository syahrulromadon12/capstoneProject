package com.app.terrestrial.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.terrestrial.core.data.source.remote.api.ApiResponse
import com.app.terrestrial.core.domain.model.UserModel
import com.app.terrestrial.core.domain.usecase.TerrestrialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val terrestrialUseCase: TerrestrialUseCase) : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)

                terrestrialUseCase.login(email, password).collect { response ->
                    if (response is ApiResponse.Success && response.data.error == false) {
                        val token = response.data.loginResult?.token ?: ""
                        val name = response.data.loginResult?.name ?: ""
                        val emailResult = response.data.loginResult?.email ?: ""
                        terrestrialUseCase.saveLoginSession(UserModel(name, emailResult, token, isLogin = true))
                        _loginResult.value = true
                    } else {
                        _loginResult.value = false
                    }
                }
            } catch (e: Exception) {
                _loginResult.value = false
                Log.e("TAG", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}