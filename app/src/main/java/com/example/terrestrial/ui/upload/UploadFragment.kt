package com.example.terrestrial.ui.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.terrestrial.R
import com.example.terrestrial.databinding.FragmentUploadBinding

class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Load animation
        val slideDown = AnimationUtils.loadAnimation(requireContext(), R.anim.down_slide)

        // Set animation to the card
        binding.cardView.startAnimation(slideDown)

        binding.button.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
