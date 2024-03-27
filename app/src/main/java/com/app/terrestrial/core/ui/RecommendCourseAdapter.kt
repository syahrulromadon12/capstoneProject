package com.app.terrestrial.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.terrestrial.core.data.source.remote.response.DataItem
import com.app.terrestrial.databinding.ItemRecommendationBinding

class RecommendCourseAdapter(private val onItemClick: (String) -> Unit) :
    ListAdapter<DataItem, RecommendCourseAdapter.CourseViewHolder>(DiffCallback) {

    class CourseViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: DataItem) {
            Glide.with(itemView).load(course.thumbnail).into(binding.ivItemPhoto)
            binding.tvCourse.text = course.courseName
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding =
            ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val currentCourse = getItem(position)
        holder.bind(currentCourse)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentCourse.id.toString())
        }
    }
}
