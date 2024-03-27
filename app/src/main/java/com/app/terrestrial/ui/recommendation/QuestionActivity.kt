package com.app.terrestrial.ui.recommendation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.terrestrial.core.ui.QuestionAdapter
import com.app.terrestrial.R
import com.app.terrestrial.databinding.ActivityQuestionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionActivity : AppCompatActivity() {

    private lateinit var questionAdapter: QuestionAdapter

    private val viewModel: QuestionViewModel by viewModels()

    private lateinit var binding: ActivityQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loading.visibility = View.GONE
        questionAdapter = QuestionAdapter { questionId, answerNumber ->
            println(getString(R.string.SelectedQuestion, questionId, answerNumber))
        }

        val recyclerView: RecyclerView = findViewById(R.id.rvQuestion)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = questionAdapter

        observeQuestions()
        viewModel.getQuestions()

        binding.submitAnswer.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            val selectedAnswers = questionAdapter.getSelectedAnswers()

            if (questionAdapter.allQuestionsAnswered()) {
                val formattedAnswers = formatSelectedAnswers(selectedAnswers)
                lifecycleScope.launch {
                    viewModel.processAnswers(
                        formattedAnswers[0],
                        formattedAnswers[1],
                        formattedAnswers[2]
                    )
                }
                Log.d("QuestionActivity", "Selected Answers: $formattedAnswers")
            } else {
                Toast.makeText(this, getString(R.string.warn_question), Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.result.observe(this) { result ->
            Log.e("QuestionActivity", "data dari QuestionActivity: $result")
            val intent = Intent(this, RecommendationResultActivity::class.java)
            intent.putExtra("result", result)
            startActivity(intent)
        }

        observeLoadingState()
    }

    private fun observeLoadingState() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun observeQuestions() {
        viewModel.questions.observe(this) { questions ->
            questions?.let {
                questionAdapter.submitList(it)
            }
        }
    }

    private fun formatSelectedAnswers(selectedAnswers: Map<Int, String>): List<Int> {
        val answerCounts = mutableMapOf("A" to 0, "B" to 0, "C" to 0)

        selectedAnswers.values.forEach { answer ->
            answerCounts[answer] = answerCounts[answer]?.plus(1) ?: 1
        }

        return answerCounts.values.toList()
    }
}