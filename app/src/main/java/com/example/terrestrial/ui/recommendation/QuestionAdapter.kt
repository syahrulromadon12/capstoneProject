package com.example.terrestrial.ui.recommendation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.terrestrial.R
import com.example.terrestrial.data.response.QuestionItem
import com.example.terrestrial.databinding.ItemQuestionBinding

class QuestionAdapter(
    private val onAnswerSelected: (Int, Int) -> Unit
) : ListAdapter<QuestionItem, QuestionAdapter.QuestionViewHolder>(QuestionDiffCallback()) {

    private val selectedAnswers = mutableMapOf<Int, String>()
    private val answeredQuestions = mutableSetOf<Int>()

    fun getSelectedAnswers(): Map<Int, String> {
        return selectedAnswers
    }

    fun allQuestionsAnswered(): Boolean {
        return answeredQuestions.size == itemCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQuestionBinding.inflate(inflater, parent, false)
        return QuestionViewHolder(binding, onAnswerSelected)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = getItem(position)
        holder.bind(question)
    }

    class QuestionViewHolder(
        private val binding: ItemQuestionBinding,
        private val onAnswerSelected: (Int, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var isAnswered = false

        fun bind(question: QuestionItem) {
            binding.tvQuestion.text = question.queText
            binding.btnOption1.text = question.answer1
            binding.btnOption2.text = question.answer2
            binding.btnOption3.text = question.answer3

            // Menetapkan warna awal (misalnya, warna default tombol)
            setButtonColor(binding.btnOption1, false)
            setButtonColor(binding.btnOption2, false)
            setButtonColor(binding.btnOption3, false)

            binding.btnOption1.setOnClickListener {
                clearSelection()
                binding.btnOption1.isSelected = true
                setButtonColor(binding.btnOption1, true) // Mengubah warna tombol
                onAnswerSelected(question.id ?: 0, 1)
                Log.d("QuestionAdapter", "Jawaban terpilih: ${question.id ?: 0}, 1")
                markQuestionAsAnswered()
            }

            binding.btnOption2.setOnClickListener {
                clearSelection()
                binding.btnOption2.isSelected = true
                setButtonColor(binding.btnOption2, true) // Mengubah warna tombol
                onAnswerSelected(question.id ?: 0, 2)
                Log.d("QuestionAdapter", "Jawaban terpilih: ${question.id ?: 0}, 2")
                markQuestionAsAnswered()
            }

            binding.btnOption3.setOnClickListener {
                clearSelection()
                binding.btnOption3.isSelected = true
                setButtonColor(binding.btnOption3, true) // Mengubah warna tombol
                onAnswerSelected(question.id ?: 0, 3)
                Log.d("QuestionAdapter", "Jawaban terpilih: ${question.id ?: 0}, 3")
                markQuestionAsAnswered()
            }
        }

        private fun clearSelection() {
            binding.btnOption1.isSelected = false
            binding.btnOption2.isSelected = false
            binding.btnOption3.isSelected = false

            // Mengembalikan warna awal ke tombol (misalnya, warna default tombol)
            setButtonColor(binding.btnOption1, false)
            setButtonColor(binding.btnOption2, false)
            setButtonColor(binding.btnOption3, false)
        }

        private fun markQuestionAsAnswered() {
            isAnswered = true
            disableOptions()
        }

        private fun disableOptions() {
            binding.btnOption1.isEnabled = !isAnswered
            binding.btnOption2.isEnabled = !isAnswered
            binding.btnOption3.isEnabled = !isAnswered
        }

        private fun setButtonColor(button: Button, isSelected: Boolean) {
            // Ubah warna tombol berdasarkan isSelected
            val colorResId = if (isSelected) R.color.white else R.color.blue
            val color = ContextCompat.getColor(button.context, colorResId)
            button.setBackgroundColor(color)
        }
    }

    private class QuestionDiffCallback : DiffUtil.ItemCallback<QuestionItem>() {
        override fun areItemsTheSame(oldItem: QuestionItem, newItem: QuestionItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: QuestionItem, newItem: QuestionItem): Boolean {
            return oldItem == newItem
        }
    }
}
