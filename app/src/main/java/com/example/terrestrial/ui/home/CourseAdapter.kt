package com.example.terrestrial.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.terrestrial.data.response.AllCourseResponse
import com.example.terrestrial.data.response.DataItem
import com.example.terrestrial.databinding.ItemCourseBinding
import com.example.terrestrial.databinding.ItemRekomendasiBinding

class CourseAdapter : ListAdapter<DataItem, CourseAdapter.CourseViewHolder>(DiffCallback) {

    class CourseViewHolder(private val binding: ItemRekomendasiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: DataItem) {
            binding.tvNameKursus.text = course.courseName

            Glide.with(itemView).load(course.thumbnail).into(binding.ivItemPhoto)
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
            ItemRekomendasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val currentCourse = getItem(position)
        holder.bind(currentCourse)
    }
}
