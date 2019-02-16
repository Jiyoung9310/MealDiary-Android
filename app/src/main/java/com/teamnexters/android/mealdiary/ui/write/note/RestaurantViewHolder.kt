package com.teamnexters.android.mealdiary.ui.write.note

import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.databinding.ViewRestaurantBinding

internal class RestaurantViewHolder(val binding: ViewRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RestaurantItem.FoundedRestaurant) {
        binding.item = item
    }

}