package com.teamnexters.android.mealdiary.ui.write.score

import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.databinding.ViewScoreBinding

internal class ScoreViewHolder(val binding: ViewScoreBinding) : RecyclerView.ViewHolder(binding.root)  {
    fun bind(item: ScoreItem) {
        binding.item = item
    }
}