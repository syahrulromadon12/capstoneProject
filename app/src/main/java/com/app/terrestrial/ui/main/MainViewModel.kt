package com.app.terrestrial.ui.main

import androidx.lifecycle.ViewModel
import com.app.terrestrial.core.data.TerrestrialRepository
import com.app.terrestrial.core.domain.model.UserModel
import com.app.terrestrial.core.domain.usecase.TerrestrialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val terrestrialUseCase: TerrestrialUseCase) : ViewModel() {
    suspend fun getSession(): Flow<UserModel> = terrestrialUseCase.getLoginSession()
}
