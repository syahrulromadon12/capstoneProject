package com.app.terrestrial.ui.recommendation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.terrestrial.R
import com.app.terrestrial.databinding.ActivityRecommendationClassBinding
import com.app.terrestrial.ui.main.MainActivity

class RecommendationResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendationClassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("RecommendationResultActivity", "onCreate is called.")

        val result = intent.getStringExtra("result")
        Log.e("RecommendationResultActivity", "Result received from QuestionActivity: $result")

        binding.tvResult.text = getString(R.string.hore, result)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}