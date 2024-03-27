package com.app.terrestrial.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.terrestrial.core.data.source.local.entity.CourseEntity
import com.app.terrestrial.core.data.source.remote.response.Data
import com.app.terrestrial.core.data.source.remote.response.DetailCourseResponse
import com.app.terrestrial.databinding.ActivityDetailCourseBinding
import com.app.terrestrial.ui.main.MainActivity
import com.bumptech.glide.Glide
import com.app.terrestrial.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailCourseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailCourseBinding

    private val detailCourseViewModel: DetailCourseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courseId = intent.getStringExtra(EXTRA_COURSE_ID)

        if (courseId != null) {
            lifecycleScope.launch {
                try {
                    detailCourseViewModel.getDetail(courseId)
                } catch (_: Exception) {
                }
            }

            binding.back.setOnClickListener { moveToMainActivity() }

            binding.btnFav.setOnClickListener {
                lifecycleScope.launch {
                    val result = detailCourseViewModel.detailCourse.value
                    if (result is com.app.terrestrial.core.data.Result.Success) {
                        val courseData = result.data?.data
                        if (courseData != null) {
                            val courseEntity = CourseEntity(
                                id = courseId.toInt(),
                                courseName = courseData.courseName,
                                thumbnail = courseData.thumbnail,
                            )
                            if (isBookmarked(courseEntity)) {
                                detailCourseViewModel.removeFromBookmark(courseEntity.id)
                                updateBookmarkIcon(false)
                            } else {
                                detailCourseViewModel.addToBookmark(courseEntity)
                                updateBookmarkIcon(true)
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            detailCourseViewModel.detailCourse
                .collect { result ->
                    handleResult(result)
                }
        }
    }

    private suspend fun isBookmarked(courseEntity: CourseEntity): Boolean {
        return detailCourseViewModel.isCourseBookmarked(courseEntity.id)
    }

    private suspend fun handleResult(result: com.app.terrestrial.core.data.Result<DetailCourseResponse?>) {
        when (result) {
            is com.app.terrestrial.core.data.Result.Success -> {
                val data = result.data?.data
                if (data != null) {
                    setData(data)
                    binding.loading.visibility = View.GONE
                    updateBookmarkIcon(isBookmarked(CourseEntity(
                        id = data.id,
                        courseName = data.courseName,
                        thumbnail = data.thumbnail,
                        )))
                } else {
                    binding.tvDesc.text = getString(R.string.null_data)
                }
            }
            is com.app.terrestrial.core.data.Result.Error -> {
                binding.loading.visibility = View.GONE
                binding.tvDesc.text = getString(R.string.error)
            }
            is com.app.terrestrial.core.data.Result.Loading -> {
                binding.loading.visibility = View.VISIBLE
            }
        }
    }

    private fun setData(data: Data) {
        Glide.with(this)
            .load(data.thumbnail)
            .into(binding.ivPic)
        binding.tvName.text = data.courseName
        binding.tvDesc.text = data.describe
        binding.tvLearn.text = data.learning
    }

    private fun moveToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun updateBookmarkIcon(isBookmarked: Boolean) {
        if (isBookmarked) {
            binding.btnFav.setImageResource(R.drawable.bookmark)
        } else {
            binding.btnFav.setImageResource(R.drawable.un_bookmark)
        }
    }

    companion object {
        const val EXTRA_COURSE_ID = "extra_course_id"
    }
}