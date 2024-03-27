package com.app.terrestrial.ui.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.terrestrial.core.data.source.remote.api.ApiResponse
import com.app.terrestrial.core.data.source.remote.response.QuestionItem
import com.app.terrestrial.core.domain.model.UserModel
import com.app.terrestrial.core.domain.usecase.TerrestrialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val terrestrialUseCase: TerrestrialUseCase) : ViewModel() {

    private val _questions = MutableLiveData<List<QuestionItem?>?>()
    val questions: LiveData<List<QuestionItem?>?> get() = _questions

    private val _result = MutableLiveData<String?>()
    val result: LiveData<String?> get() = _result

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var isQuestionFetched = false

    fun getQuestions() {
        if (!isQuestionFetched) {
            viewModelScope.launch {
                terrestrialUseCase.getQuestion().collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _questions.value = response.data.data
                            isQuestionFetched = true
                        }
                        is ApiResponse.Error -> {
                            /* handle error */
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    suspend fun processAnswers(desain: Int, logika: Int, data: Int): String {
        val result = terrestrialUseCase.processAnswers(desain, logika, data)
        _result.postValue(result)
        return result
    }
}