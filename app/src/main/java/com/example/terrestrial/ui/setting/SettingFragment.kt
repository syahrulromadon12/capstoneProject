package com.example.terrestrial.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.terrestrial.R
import com.example.terrestrial.databinding.FragmentSettingBinding
import com.example.terrestrial.ui.ViewModelFactory

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingViewModel by viewModels { ViewModelFactory.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.name.observe(viewLifecycleOwner) {
            binding.name.text = it
        }

        viewModel.email.observe(viewLifecycleOwner) {
            binding.email.text = it
        }

        viewModel.profile.observe(viewLifecycleOwner) { imageUrl ->
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.elon_musk)
                .circleCrop()
                .into(binding.photo)
        }

        binding.language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.theme.setOnCheckedChangeListener { _, isChecked ->
            val nightMode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }

        binding.logout.setOnClickListener {
            viewModel.logout()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
