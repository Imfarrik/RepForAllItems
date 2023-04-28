package com.example.nasa.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasa.databinding.PhotoItemBinding
import com.example.nasa.model.Photo

class PhotoAdapter(
    private val data: List<Photo>,
    private val listener: (String) -> Unit
) :
    RecyclerView.Adapter<PhotoAdapter.TasksVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksVH {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksVH(binding)
    }

    override fun onBindViewHolder(holder: TasksVH, position: Int) {
        val task = data[position]
        holder.initView(task, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class TasksVH(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun initView(item: Photo, pos: Int) {

            itemView.setOnClickListener {
                listener(item.img_src)
            }

            Glide.with(itemView.context)
                .load(item.img_src)
                .into(binding.imageItem)

            binding.earthDate.text = item.earth_date
            binding.cameraName.text = item.camera.name
            binding.landingDate.text = item.rover.landing_date
            binding.roverName.text = item.rover.name

        }

    }
}