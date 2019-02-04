package com.teamnexters.android.mealdiary.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.databinding.ViewDiaryBinding
import androidx.databinding.DataBindingUtil
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.data.local.entity.Diary


internal class DiaryAdapter : ListAdapter<ListItem, DiaryViewHolder>(object : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.listItemId == newItem.listItemId
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.listItemId == newItem.listItemId
    }

}) {

    companion object {
        @JvmStatic
        @BindingAdapter("arrtext")
        fun AppCompatTextView.arrText(diary: Diary){
            var s: String = ""
            for(i in diary.hashTags) {
                s += i.tagName + " "
            }
            this.text = s
        }

        @JvmStatic
        @BindingAdapter("inttext")
        fun AppCompatTextView.intText(score: Int){
            this.text = score.toString()
        }
    }

    interface Callbacks {
        fun onClickDiary(item: ListItem.DiaryItem)
    }

    private var callbacks: Callbacks? = null

    fun setCallbacks(callbacks: Callbacks) {
        this.callbacks = callbacks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        //val binding = ViewDiaryBinding.inflate(inflater, parent, false)
        val binding = DataBindingUtil.inflate<ViewDiaryBinding>(inflater, R.layout.view_diary, parent, false)

        val viewHolder =  DiaryViewHolder(binding)

        binding.clickListener = View.OnClickListener {
            callbacks?.onClickDiary(getItem(viewHolder.adapterPosition) as ListItem.DiaryItem)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        if(holder.adapterPosition != RecyclerView.NO_POSITION) {
            holder.bind(getItem(position) as ListItem.DiaryItem)
            holder.imgload()
        }
    }

}