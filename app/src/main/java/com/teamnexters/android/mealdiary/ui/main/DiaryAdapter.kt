package com.teamnexters.android.mealdiary.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.databinding.ViewDiaryBinding

internal class DiaryAdapter : ListAdapter<ListItem, DiaryViewHolder>(object : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.id == newItem.id
    }

}) {

    interface Callbacks {
        fun onClickDiary(item: ListItem.DiaryItem)
    }

    private var callbacks: Callbacks? = null

    fun setCallbacks(callbacks: Callbacks) {
        this.callbacks = callbacks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ViewDiaryBinding.inflate(inflater, parent, false)

        val viewHolder =  DiaryViewHolder(binding)

        binding.clickListener = View.OnClickListener {
            callbacks?.onClickDiary(getItem(viewHolder.adapterPosition) as ListItem.DiaryItem)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        if(holder.adapterPosition != RecyclerView.NO_POSITION) {
            holder.bind(getItem(position) as ListItem.DiaryItem)
        }
    }

}