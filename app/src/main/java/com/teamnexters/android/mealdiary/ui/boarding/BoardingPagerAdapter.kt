package com.teamnexters.android.mealdiary.ui.boarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.databinding.ViewBoardBinding

internal class BoardingPagerAdapter:ListAdapter<BoardItem, BoardingViewHolder> (object : DiffUtil.ItemCallback<BoardItem>() {

    override fun areItemsTheSame(oldItem: BoardItem, newItem: BoardItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: BoardItem, newItem: BoardItem): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardingViewHolder {
        val binding = DataBindingUtil.inflate<ViewBoardBinding>(LayoutInflater.from(parent.context), R.layout.view_board, parent, false)

        return BoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}