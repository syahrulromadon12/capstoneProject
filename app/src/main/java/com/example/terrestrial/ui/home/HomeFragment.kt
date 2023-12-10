package com.example.terrestrial.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.terrestrial.data.api.ApiConfig
import com.example.terrestrial.data.auth.UserRepository
import com.example.terrestrial.databinding.FragmentHomeBinding
import com.example.terrestrial.ui.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.rvRecommend
        val adapter = CourseAdapter()

        homeViewModel.courseList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        homeViewModel.getAllCourse()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
