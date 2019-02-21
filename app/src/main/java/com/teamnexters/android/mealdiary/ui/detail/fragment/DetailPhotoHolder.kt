package com.teamnexters.android.mealdiary.ui.detail.fragment

import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.databinding.ViewDetailPhotoBinding

internal class DetailPhotoHolder(private val binding: ViewDetailPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DetailPhoto) {
        binding.item = item
    }
}