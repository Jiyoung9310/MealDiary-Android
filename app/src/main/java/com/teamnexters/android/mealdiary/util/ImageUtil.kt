package com.teamnexters.android.mealdiary.util

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.centerCropTransform
import com.teamnexters.android.mealdiary.R

class ImageUtil {

    private val TAG = "ImageUtil"

    fun setImage(context: Context, url: String, view: ImageView) {
        Glide.with(context)
                .load(url)
                .apply(centerCropTransform())
                .into(view)
        val drawable = context.getDrawable(R.drawable.bg_round_corner) as GradientDrawable
        view.run {
            background = drawable
            clipToOutline = true
        }
    }

    fun setImage(context: Context, id: Int, view: ImageView) {
        Glide.with(context)
                .load(id)
                .apply(centerCropTransform())
                .into(view)
        val drawable = context.getDrawable(R.drawable.bg_round_corner) as GradientDrawable
        view.run {
            background = drawable
            clipToOutline = true
        }
    }
}
