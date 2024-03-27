package com.app.terrestrial.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.terrestrial.core.data.source.remote.api.ApiResponse
import com.app.terrestrial.core.domain.usecase.TerrestrialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val useCase: TerrestrialUseCase) : ViewModel() {

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> = _registrationResult

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _registrationResult.postValue(false)

                val response = useCase.signup(name, email, password).firstOrNull()
                val registrationSuccess = response is ApiResponse.Success

                _registrationResult.postValue(registrationSuccess)
            } catch (e: Exception) {
                _registrationResult.postValue(false)
            }
        }
    }
}