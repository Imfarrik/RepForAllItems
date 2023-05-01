package com.example.permissions.ui.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.permissions.databinding.PhotoItemBinding
import com.example.permissions.model.PhotoModel

class PhotoAdapter(
    private val data: MutableList<PhotoModel>,
    private val isDateNeeded: Boolean,
    private val deleteListener: (PhotoModel, Int) -> Unit
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
        fun initView(item: PhotoModel, pos: Int) {

            if (isDateNeeded) {
                binding.container.isVisible = true
                binding.txtDate.text = item.date
            }

            Glide.with(itemView.context)
                .load(Uri.parse(item.uri))
                .into(binding.imageItem)

            binding.btnX.setOnClickListener {
                deleteListener(item, pos)
                data.remove(item)
                notifyDataSetChanged()
            }
        }

    }
}