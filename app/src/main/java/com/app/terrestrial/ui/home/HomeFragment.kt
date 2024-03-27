package com.app.terrestrial.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.terrestrial.core.domain.model.UserModel
import com.app.terrestrial.core.ui.CourseAdapter
import com.app.terrestrial.core.ui.RecommendCourseAdapter
import com.app.terrestrial.ui.detail.DetailCourseActivity
import com.bumptech.glide.Glide
import com.app.terrestrial.R
import com.app.terrestrial.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private var isDataLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lifecycleScope.launch {
            homeViewModel.getSession().observe(viewLifecycleOwner) { user: UserModel ->
                loadData()
                binding.tvName.text = user.name
                binding.tvTag.text = getString(R.string.tag_line)
            }
        }

        homeViewModel.profile.observe(viewLifecycleOwner) { imageUrl ->
            Log.d("ProfileImage", "Image URL: $imageUrl")
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.default_profile)
                .circleCrop()
                .into(binding.photo)
        }

        return root
    }

    private fun loadData() {
        val courseRecyclerView: RecyclerView = binding.rvCourse
        val courseAdapter = CourseAdapter { courseId ->
            navigateToDetail(courseId)
        }

        homeViewModel.courseList.observe(viewLifecycleOwner) {
            courseAdapter.submitList(it)
        }

        courseRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        courseRecyclerView.adapter = courseAdapter

        val recommendRecyclerView: RecyclerView = binding.rvRecommend
        val recommendAdapter = RecommendCourseAdapter { courseId ->
            navigateToDetail(courseId)
        }

        homeViewModel.recommendCourseList.observe(viewLifecycleOwner) {
            recommendAdapter.submitList(it)
        }

        recommendRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recommendRecyclerView.adapter = recommendAdapter

        homeViewModel.getAllCourse()
        homeViewModel.getRecommendCourse()
    }

    private fun navigateToDetail(courseId: String) {
        val intent = Intent(requireContext(), DetailCourseActivity::class.java)
        intent.putExtra(DetailCourseActivity.EXTRA_COURSE_ID, courseId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}