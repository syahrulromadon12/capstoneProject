package com.app.terrestrial.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.terrestrial.core.ui.FavoriteAdapter
import com.app.terrestrial.ui.detail.DetailCourseActivity
import com.app.terrestrial.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!

    private lateinit var favoriteAdapter: FavoriteAdapter

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        favoriteViewModel.fetchAllFavoriteCourses()
        observeCourses()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter { courseId ->
            navigateToDetail(courseId)
        }
        binding.rvFavCourse.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
        }
    }

    private fun observeCourses() {
        favoriteViewModel.favoriteCourses.observe(viewLifecycleOwner) { courses ->
            favoriteAdapter.submitList(courses)
        }
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