package com.app.terrestrial.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.terrestrial.core.domain.model.UserModel
import com.bumptech.glide.Glide
import com.app.terrestrial.R
import com.app.terrestrial.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lifecycleScope.launch {
            viewModel.getSession().observe(viewLifecycleOwner) { user: UserModel ->
                binding.name.text = user.name
                binding.email.text = user.email
            }
        }

        viewModel.profile.observe(viewLifecycleOwner) { imageUrl ->
            Log.d("ProfileImage", "Image URL: $imageUrl")
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.default_profile)
                .circleCrop()
                .into(binding.photo)
        }

        binding.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
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
