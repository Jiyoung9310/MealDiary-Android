package com.teamnexters.android.mealdiary.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.databinding.ViewDiaryBinding

internal class DiaryViewHolder(private val binding: ViewDiaryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ListItem.DiaryItem) {
        binding.item = item
    }
}