package com.teamnexters.android.mealdiary.ui.detail.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.databinding.ViewDetailPhotoBinding

internal class DetailPhotoAdapter : ListAdapter<DetailPhoto, DetailPhotoHolder>(object : DiffUtil.ItemCallback<DetailPhoto>() {
    override fun areItemsTheSame(oldItem: DetailPhoto, newItem: DetailPhoto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DetailPhoto, newItem: DetailPhoto): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPhotoHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ViewDetailPhotoBinding>(inflater, R.layout.view_detail_photo, parent, false)

        return DetailPhotoHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailPhotoHolder, position: Int) {
        //if(holder.adapterPosition != ViewPager2.) {
            holder.bind(getItem(position))
        //}
    }
}