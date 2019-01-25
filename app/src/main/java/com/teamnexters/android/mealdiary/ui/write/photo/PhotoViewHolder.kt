package com.teamnexters.android.mealdiary.ui.write.photo

import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.databinding.ViewPhotoBinding

internal class PhotoViewHolder(val binding: ViewPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photo: Photo) {
        binding.photo = photo
    }

}