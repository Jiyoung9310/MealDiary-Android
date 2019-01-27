package com.teamnexters.android.mealdiary.ui.write.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.teamnexters.android.mealdiary.databinding.ViewPhotoBinding

internal class PhotoAdapter : ListAdapter<Photo, PhotoViewHolder>(object : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.imgPath == newItem.imgPath
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.imgPath == newItem.imgPath
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ViewPhotoBinding.inflate(LayoutInflater.from(parent.context))

        val viewHolder = PhotoViewHolder(binding)

        return viewHolder
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}