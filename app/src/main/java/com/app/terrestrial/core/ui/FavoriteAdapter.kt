package com.app.terrestrial.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.terrestrial.core.data.source.local.entity.CourseEntity
import com.app.terrestrial.databinding.ItemCourseBinding
import com.bumptech.glide.Glide

class FavoriteAdapter(private val onItemClick: (String) -> Unit) :
    ListAdapter<CourseEntity, FavoriteAdapter.CourseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position)
        holder.bind(course)
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: CourseEntity) {
            binding.root.setOnClickListener {
                onItemClick(course.id.toString())
            }
            Glide.with(binding.ivItemPhoto)
                .load(course.thumbnail)
                .into(binding.ivItemPhoto)
            binding.tvTitleKursus.text = course.courseName
            binding.desc.text = course.courseType
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<CourseEntity>() {
        override fun areItemsTheSame(oldItem: CourseEntity, newItem: CourseEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseEntity, newItem: CourseEntity): Boolean {
            return oldItem == newItem
        }
    }
}
