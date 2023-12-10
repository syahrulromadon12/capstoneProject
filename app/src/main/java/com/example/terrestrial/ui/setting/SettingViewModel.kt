package com.example.terrestrial.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.terrestrial.R
import com.example.terrestrial.data.auth.UserPreferences
import com.example.terrestrial.data.auth.UserRepository
import kotlinx.coroutines.launch


class SettingViewModel(private val repository: UserRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Setting"
    }
    val text: LiveData<String> = _text

    private val _name = MutableLiveData<String>().apply {
        value = "Asep Sutisna"
    }
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>().apply {
        value = "AsepSutisna765@gmail.com"
    }
    val email: LiveData<String> = _email

    private val _profile = MutableLiveData<String>().apply {
        val drawableResourceId = R.drawable.elon_musk
        value = drawableResourceId.toString()
    }
    val profile: LiveData<String> = _profile

    private val _darkMode = MutableLiveData<Boolean>()
    val darkMode: LiveData<Boolean> = _darkMode

    fun logout() {
        viewModelScope.launch {
            repository.clearLoginSession()
        }
    }
}
