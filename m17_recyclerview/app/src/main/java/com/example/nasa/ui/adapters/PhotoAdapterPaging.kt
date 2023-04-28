package com.example.nasa.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasa.databinding.PhotoItemBinding
import com.example.nasa.model.Photo

class PhotoAdapterPaging(
    private val listener: (String) -> Unit
) : PagingDataAdapter<Photo, PhotoAdapterPaging.TasksVH>(DataDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksVH {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksVH(binding)
    }

    override fun onBindViewHolder(holder: TasksVH, position: Int) {
        val task = getItem(position)
        holder.initView(task)
    }


    inner class TasksVH(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun initView(item: Photo?) {

            if (item != null) {

                itemView.setOnClickListener {
                    listener(item.img_src)
                }

                Glide.with(itemView.context).load(item.img_src).into(binding.imageItem)

                binding.earthDate.text = item.earth_date
                binding.cameraName.text = item.camera.name
                binding.landingDate.text = item.rover.landing_date
                binding.roverName.text = item.rover.name
            }

        }

    }

    private object DataDiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }


    }
}