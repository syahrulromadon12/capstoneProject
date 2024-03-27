package com.app.terrestrial.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.terrestrial.R
import com.app.terrestrial.core.domain.model.UserModel
import com.app.terrestrial.core.domain.usecase.TerrestrialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val terrestrialUseCase: TerrestrialUseCase) : ViewModel() {
    private val _profile = MutableLiveData<String>().apply {
        val drawableResourceId = R.drawable.default_profile
        value = drawableResourceId.toString()
    }
    val profile: LiveData<String> = _profile

    fun logout() {
        viewModelScope.launch {
            terrestrialUseCase.clearLoginSession()
        }
    }

    suspend fun getSession(): LiveData<UserModel> = terrestrialUseCase.getLoginSession().asLiveData()
}
