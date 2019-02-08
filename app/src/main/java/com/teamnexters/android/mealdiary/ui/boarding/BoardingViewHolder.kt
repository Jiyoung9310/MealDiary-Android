package com.teamnexters.android.mealdiary.ui.boarding

import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.databinding.ViewBoardBinding

internal class BoardingViewHolder(val binding: ViewBoardBinding) : RecyclerView.ViewHolder(binding.root)  {
    fun bind(item: BoardItem) {
        binding.item = item
    }
}