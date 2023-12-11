package com.example.terrestrial.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.terrestrial.data.auth.Result
import com.example.terrestrial.R
import com.example.terrestrial.data.auth.UserModel
import com.example.terrestrial.data.response.LoginResponse
import com.example.terrestrial.databinding.ActivityLoginBinding
import com.example.terrestrial.ui.ViewModelFactory
import com.example.terrestrial.ui.main.MainActivity
import com.example.terrestrial.ui.main.MainViewModel
import com.example.terrestrial.ui.signup.SignupActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)

        binding.tvSignup.setOnClickListener {
            val signup = Intent(this, SignupActivity::class.java)
            startActivity(signup)
        }

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            showLoading(true)

            viewModel.loginUser(email, password)

            viewModel.loginResult.observe(this) { isLogin ->
                showLoading(false)

                if (isLogin) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                } else {
                    showToast(getString(R.string.login_failed))
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.imageView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(200)
        val emailEditText =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(200)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(200)
        val passwordEditText =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(200)
        val login =
            ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(200)
        val tvSignup =
            ObjectAnimator.ofFloat(binding.tvquestionSignup, View.ALPHA, 1f).setDuration(300)
        val signup =
            ObjectAnimator.ofFloat(binding.tvSignup, View.ALPHA, 1f).setDuration(300)

        val together = AnimatorSet().apply {
            playTogether(login, tvSignup, signup)
        }

        AnimatorSet().apply {
            playSequentially(
                emailTextView,
                emailEditText,
                passwordTextView,
                passwordEditText,
                login,
                together
            )
            startDelay = 90
        }.start()
    }

}
