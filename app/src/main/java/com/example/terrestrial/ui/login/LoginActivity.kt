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
import com.example.terrestrial.data.auth.Result
import com.example.terrestrial.R
import com.example.terrestrial.data.auth.UserModel
import com.example.terrestrial.databinding.ActivityLoginBinding
import com.example.terrestrial.ui.ViewModelFactory
import com.example.terrestrial.ui.main.MainActivity
import com.example.terrestrial.ui.main.MainViewModel
import com.example.terrestrial.ui.signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener { processLogin() }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        setupView()
        playAnimation()
    }

    private fun processLogin() {
        binding.apply {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            viewModel.login(email, password).observe(this@LoginActivity) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                        loginButton.isEnabled = false
                    }
                    is Result.Success -> {
                        showLoading(false)
                        loginButton.isEnabled = true
                        val token = result.data?.loginResult?.token.orEmpty()
                        val name = result.data?.loginResult?.name.orEmpty()
                        val resultEmail = result.data?.loginResult?.email.orEmpty()
                        val user = UserModel(name, resultEmail, token, isLogin = true)
                        viewModel.setLogin(user)
                        showToast(getString(R.string.login_succes))
                        moveToMainActivity()
                    }
                    is Result.Error -> {
                        showLoading(false)
                        loginButton.isEnabled = true
                        showToast(getString(R.string.login_failed))
                    }
                }
            }
        }
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
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