package com.example.terrestrial.ui.recommendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.terrestrial.R
import com.example.terrestrial.databinding.ActivityQuestionBinding
import com.example.terrestrial.ui.ViewModelFactory

class QuestionActivity : AppCompatActivity() {

    private lateinit var questionAdapter: QuestionAdapter
    private val viewModel by viewModels<QuestionViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionAdapter = QuestionAdapter { questionId, answerNumber ->
            println("Jawaban terpilih untuk pertanyaan $questionId: $answerNumber")
        }

        val recyclerView: RecyclerView = findViewById(R.id.rvQuestion)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = questionAdapter

        observeQuestions()
        viewModel.getQuestions()

        binding.submitAnswer.setOnClickListener {
            val selectedAnswers = questionAdapter.getSelectedAnswers()

            if (questionAdapter.allQuestionsAnswered()) {
                // Memanggil fungsi untuk memproses model dengan hasil jawaban terpilih
                viewModel.processModelWithAnswers(selectedAnswers)
            } else {
                // Tampilkan pesan bahwa semua pertanyaan harus dijawab
                Toast.makeText(this, "Harap jawab semua pertanyaan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeQuestions() {
        viewModel.questions.observe(this) { questions ->
            questions?.let {
                questionAdapter.submitList(it)
            }
        }
    }
}
