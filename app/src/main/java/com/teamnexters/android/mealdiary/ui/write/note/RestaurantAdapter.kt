package com.teamnexters.android.mealdiary.ui.write.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.databinding.ViewRestaurantBinding


internal class RestaurantAdapter : ListAdapter<RestaurantItem, RestaurantViewHolder>(object : DiffUtil.ItemCallback<RestaurantItem>() {
    override fun areItemsTheSame(oldItem: RestaurantItem, newItem: RestaurantItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RestaurantItem, newItem: RestaurantItem): Boolean {
        return oldItem == newItem
    }

}) {

    interface Callbacks {
        fun onClickRestaurantItem(restaurantItem: RestaurantItem)
    }

    private var callbacks: Callbacks? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = DataBindingUtil.inflate<ViewRestaurantBinding>(LayoutInflater.from(parent.context), R.layout.view_restaurant, parent, false)

        val viewHolder = RestaurantViewHolder(binding)

        binding.clickListener = View.OnClickListener {
            if(viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                callbacks?.onClickRestaurantItem(getItem(viewHolder.adapterPosition))
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setCallbacks(callbacks: Callbacks) {
        this.callbacks = callbacks
    }

}