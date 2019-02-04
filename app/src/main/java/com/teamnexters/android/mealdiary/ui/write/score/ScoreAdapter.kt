package com.teamnexters.android.mealdiary.ui.write.score

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.databinding.ViewScoreBinding

internal class ScoreAdapter : ListAdapter<ScoreItem, ScoreViewHolder>(object : DiffUtil.ItemCallback<ScoreItem>() {
    override fun areItemsTheSame(oldItem: ScoreItem, newItem: ScoreItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ScoreItem, newItem: ScoreItem): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = DataBindingUtil.inflate<ViewScoreBinding>(LayoutInflater.from(parent.context), R.layout.view_score, parent, false)

        return ScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
