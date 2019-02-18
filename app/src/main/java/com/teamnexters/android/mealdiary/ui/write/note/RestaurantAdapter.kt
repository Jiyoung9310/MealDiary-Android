package com.teamnexters.android.mealdiary.ui.write.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.databinding.ViewReataurantNotFoundBinding
import com.teamnexters.android.mealdiary.databinding.ViewRestaurantBinding
import java.lang.RuntimeException


internal class RestaurantAdapter : ListAdapter<RestaurantItem, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<RestaurantItem>() {
    override fun areItemsTheSame(oldItem: RestaurantItem, newItem: RestaurantItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RestaurantItem, newItem: RestaurantItem): Boolean {
        return oldItem == newItem
    }

}) {

    companion object {
        private const val TYPE_RESTAURANT = 100
        private const val TYPE_NOT_FOUND = 101
    }

    interface Callbacks {
        fun onClickRestaurantItem(restaurantItem: RestaurantItem)
    }

    private var callbacks: Callbacks? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_RESTAURANT -> {
                val binding = DataBindingUtil.inflate<ViewRestaurantBinding>(LayoutInflater.from(parent.context), R.layout.view_restaurant, parent, false)

                val viewHolder = RestaurantViewHolder(binding)

                binding.clickListener = View.OnClickListener {
                    if(viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                        callbacks?.onClickRestaurantItem(getItem(viewHolder.adapterPosition))
                    }
                }

                viewHolder
            }
            TYPE_NOT_FOUND -> {
                val binding = DataBindingUtil.inflate<ViewReataurantNotFoundBinding>(LayoutInflater.from(parent.context), R.layout.view_reataurant_not_found, parent, false)

                val viewHolder = NotFoundRestaurantViewHolder(binding)

                binding.clickListener = View.OnClickListener {
                    if(viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                        callbacks?.onClickRestaurantItem(getItem(viewHolder.adapterPosition))
                    }
                }

                viewHolder
            }
            else -> {
                throw RuntimeException("invalid viewType = $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is RestaurantViewHolder -> {
                holder.bind(getItem(position) as RestaurantItem.FoundedRestaurant)
            }
            is NotFoundRestaurantViewHolder -> {
                holder.bind(getItem(position) as RestaurantItem.NotFound)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is RestaurantItem.FoundedRestaurant -> TYPE_RESTAURANT
            is RestaurantItem.NotFound -> TYPE_NOT_FOUND
        }
    }

    fun setCallbacks(callbacks: Callbacks) {
        this.callbacks = callbacks
    }

}