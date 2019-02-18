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

    interface Callbacks {
        fun onSelectedPhotos(selectedPhotos: List<Photo>)
    }

    private var items: List<Photo> = emptyList()

    private var selectedPhotoList = mutableListOf<Photo>()

    private var callbacks: Callbacks? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ViewPhotoBinding.inflate(LayoutInflater.from(parent.context))

        val viewHolder = PhotoViewHolder(binding)

        binding.clickListener = View.OnClickListener {
            if(viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                val item = getItem(viewHolder.adapterPosition)

                val selected = !item.selected

                if(selected && selectedPhotoList.size < 6) {
                    selectedPhotoList.add(item)

                    item.selected = selected
                    item.order = selectedPhotoList.size

                    notifyItemChanged(viewHolder.adapterPosition, PAYLOAD_SELECT_ICON)
                } else if(!selected) {
                    selectedPhotoList.remove(item)

                    val removedItemOrder = item.order

                    item.selected = selected
                    item.order = 0

                    selectedPhotoList
                            .filter { selectedItem -> removedItemOrder < selectedItem.order }
                            .forEach { selectedItem ->
                                selectedItem.order -= 1
                            }

                    notifyItemChanged(viewHolder.adapterPosition, PAYLOAD_SELECT_ICON)
                }

                setSelectedPhotoList(selectedPhotoList)

                callbacks?.onSelectedPhotos(selectedPhotoList)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        bind(holder, position)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int, payloads: MutableList<Any>) {
        bind(holder, position)
    }

    fun setSelectedPhotoList(selectedPhotos: List<Photo>) {
        items.forEachIndexed { index, photo ->
            selectedPhotos.firstOrNull { selectedPhoto -> selectedPhoto.imgPath == photo.imgPath }?.let {
                val selectedPosition = items.indexOf(it)

                if(selectedPosition != -1) {
                    photo.selected = it.selected
                    photo.order = it.order

                    notifyItemChanged(index, PAYLOAD_SELECT_ICON)
                }
            }
        }
    }

    fun setCallbacks(callbacks: Callbacks) {
        this.callbacks = callbacks
    }

    private fun bind(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<Photo>?) {
        super.submitList(list)

        list?.let {
            items = it
        }
    }

}