package com.teamnexters.android.mealdiary.ui.write.note

import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.databinding.ViewReataurantNotFoundBinding

internal class NotFoundRestaurantViewHolder(val binding: ViewReataurantNotFoundBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RestaurantItem.NotFound) {
        binding.item = item
    }
}