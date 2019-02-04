package com.teamnexters.android.mealdiary.ui.write.photo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.BehaviorRelay
import com.teamnexters.android.mealdiary.databinding.ViewPhotoBinding
import io.reactivex.Observable

internal class PhotoAdapter : ListAdapter<Photo, PhotoViewHolder>(object : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.imgPath == newItem.imgPath
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.imgPath == newItem.imgPath
    }
}) {

    companion object {
        const val PAYLOAD_SELECT_ICON = "payload_select_icon"
    }

    private var selectedPositions = mutableListOf<Int>()
    private val selectedPhotoListRelay = BehaviorRelay.createDefault<List<Photo>>(listOf())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ViewPhotoBinding.inflate(LayoutInflater.from(parent.context))

        val viewHolder = PhotoViewHolder(binding)

        binding.clickListener = View.OnClickListener {
            if(viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                val item = getItem(viewHolder.adapterPosition)

                val selected = !item.selected

                if(selected && selectedPositions.size < 6) {
                    selectedPositions.add(viewHolder.adapterPosition)

                    item.selected = selected
                    item.order = selectedPositions.size

                    notifyItemChanged(viewHolder.adapterPosition, PAYLOAD_SELECT_ICON)
                } else if(!selected) {
                    selectedPositions.remove(viewHolder.adapterPosition)

                    val removedItemOrder = item.order

                    item.selected = selected
                    item.order = 0

                    selectedPositions
                            .filter { selectedPosition -> removedItemOrder < getItem(selectedPosition).order }
                            .forEach { selectedPosition ->
                                getItem(selectedPosition).order = getItem(selectedPosition).order - 1

                                notifyItemChanged(selectedPosition, PAYLOAD_SELECT_ICON)
                            }

                    notifyItemChanged(viewHolder.adapterPosition, PAYLOAD_SELECT_ICON)
                }

                selectedPhotoListRelay.accept(selectedPhotoList())
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.bind(getItem(position))
    }

    fun selectedPhotoListObservable(): Observable<List<Photo>> {
        return selectedPhotoListRelay
    }

    private fun selectedPhotoList(): List<Photo> {
        return selectedPositions.map { getItem(it) }
    }

}