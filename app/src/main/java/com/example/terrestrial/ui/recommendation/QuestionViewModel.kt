package com.example.terrestrial.ui.recommendation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.terrestrial.data.auth.UserRepository
import com.example.terrestrial.data.response.QuestionItem
import com.example.terrestrial.data.auth.Result
import kotlinx.coroutines.launch

class QuestionViewModel(private val repository: UserRepository) : ViewModel() {
    private val _questions = MutableLiveData<List<QuestionItem?>?>()
    val questions: LiveData<List<QuestionItem?>?> get() = _questions

    fun getQuestions() {
        viewModelScope.launch {
            when (val result = repository.getQuestion()) {
                is Result.Success -> _questions.value = result.data.data
                is Result.Error -> {/* handle error */}
                else -> {}
            }
        }
    }

    fun processModelWithAnswers(selectedAnswers: Map<Int, String>, context: Context) {
        viewModelScope.launch {
            val answerList = mutableListOf<Float>()
            selectedAnswers.forEach { (_, answer) ->
                val numericAnswer = when (answer.toUpperCase()) {
                    "A" -> 1.0
                    "B" -> 2.0
                    "C" -> 3.0
                    else -> 0.0
                }
                answerList.add(numericAnswer.toFloat())
            }

            val result = repository.processModel(context, answerList.toFloatArray())
            handleModelResult(result)
        }
    }

    private fun handleModelResult(result: FloatArray) {
        Log.d("QuestionViewModel", "Hasil dari model: ${result.contentToString()}")
    }
}
