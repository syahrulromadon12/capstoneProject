package com.example.terrestrial.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.terrestrial.data.auth.Result
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.terrestrial.R
import com.example.terrestrial.databinding.ActivitySignupBinding
import com.example.terrestrial.ui.ViewModelFactory
import com.example.terrestrial.ui.login.LoginActivity
import com.example.terrestrial.ui.main.MainViewModel


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener { processRegister() }

        binding.tvLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        setupView()
        setupAnimation()
    }

    private fun setupAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(200)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(200)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(200)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(200)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(200)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(200)
        val RegisterButton =
            ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(200)
        val tvlogin =
            ObjectAnimator.ofFloat(binding.tvquestionLogin, View.ALPHA, 1f).setDuration(200)
        val login =
            ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(200)

        val together = AnimatorSet().apply {playTogether(RegisterButton, tvlogin, login)}

        AnimatorSet().apply {
            playSequentially(
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                RegisterButton,
                login,
                together
            )
            startDelay = 90
        }.start()
    }


    private fun processRegister() {

        binding.apply {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signup(name, email, password).observe(this@SignupActivity) { result ->
                    Log.wtf("Result Signup", result.toString())
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                showLoading(true)
                                signupButton.isEnabled = false
                            }

                            is Result.Success -> {
                                showLoading(false)
                                signupButton.isEnabled = true
                                showToast(getString(R.string.create_account_succes))
                                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                            is Result.Error -> {
                                showLoading(false)
                                signupButton.isEnabled = true
                                showToast(getString(R.string.create_account_failed))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
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
                WindowManager.LayoutParams.FLAGS_CHANGED,
                WindowManager.LayoutParams.FLAGS_CHANGED
            )
        }
        supportActionBar?.hide()
    }
}