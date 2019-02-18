package com.teamnexters.android.mealdiary.util

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.ui.ToolbarResources
import com.teamnexters.android.mealdiary.util.extension.hasResource
import com.teamnexters.android.mealdiary.util.extension.toPx
import io.reactivex.functions.Action
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

@BindingAdapter("android:visibility")
internal fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if(isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("android:minWidth")
internal fun setMinWidth(view: TextView, dp: Int) {
    view.minimumWidth = dp.toPx
}

@BindingAdapter("android:text")
internal fun setText(textView: TextView, @StringRes resId: Int) {
    if(resId != 0) {
        textView.setText(resId)
    }
}

@BindingAdapter("android:textColor")
internal fun setTextColor(textView: TextView, colorOrResId: Int) {
    if(textView.context.hasResource(colorOrResId)) {
        val resId = if(colorOrResId == 0) {
            null
        } else {
            AppCompatResources.getColorStateList(textView.context, colorOrResId)
        }

        textView.setTextColor(resId)
    } else {
        textView.setTextColor(colorOrResId)
    }
}

@BindingAdapter("android:textAppearance")
internal fun setTextAppearance(textView: TextView, @StyleRes resId: Int) {
    if(resId == 0) {
        return
    }

    TextViewCompat.setTextAppearance(textView, resId)
}

@BindingAdapter("textDate")
internal fun setTextDate(textView: TextView, date: ZonedDateTime?) {
    date?.let {
        textView.text = DateTimeFormatter.ofPattern("yyyy.MM.dd").format(it)
    }
}

@BindingAdapter("android:background")
internal fun setBackground(view: View, colorOrResId: Int) {
    if(view.context.hasResource(colorOrResId)) {
        ViewCompat.setBackground(view, ContextCompat.getDrawable(view.context, colorOrResId))
    } else {
        ViewCompat.setBackground(view, ColorDrawable(colorOrResId))
    }
}

@BindingAdapter("srcCompat")
internal fun setSrcCompat(imageView: ImageView, colorOrResId: Int) {
    val drawable = if(imageView.context.hasResource(colorOrResId)) {
        ContextCompat.getDrawable(imageView.context, colorOrResId)
    } else {
        ColorDrawable(colorOrResId)
    }

    imageView.setImageDrawable(drawable)
}

@BindingAdapter("srcCompat")
internal fun setSrcCompat(imageView: ImageView, url: String) {
    Glide.with(imageView)
            .load(url)
            .apply(RequestOptions().centerCrop())
            .into(imageView)
}

@BindingAdapter("srcCompatCorner")
internal fun setSrcCompatCorner(imageView: ImageView, url: String?) {
    Glide.with(imageView)
            .load(url)
            .apply(RequestOptions().centerCrop())
            .into(imageView)

    val drawable = imageView.context.getDrawable(R.drawable.bg_round_corner) as GradientDrawable
    imageView.run {
        background = drawable
        clipToOutline = true
    }
}

@BindingAdapter(value = [
    "srcCompat", "circle"
], requireAll = false)
internal fun setSrcCompat(
        imageView: ImageView,
        url: String?,
        circle: Boolean
) {
    var requestBuilder = Glide.with(imageView).load(url)

    if(circle) {
        requestBuilder = requestBuilder.apply(RequestOptions.bitmapTransform(CircleCrop()))
    }

    requestBuilder.into(imageView)
}

@BindingAdapter(value = [
    "srcCompat", "circle"
], requireAll = false)
internal fun setSrcCompat(
        imageView: ImageView,
        @DrawableRes resId: Int,
        circle: Boolean
) {
    var requestBuilder = Glide.with(imageView).load(resId)

    if(circle) {
        requestBuilder = requestBuilder.apply(RequestOptions.bitmapTransform(CircleCrop()))
    }

    requestBuilder.into(imageView)
}

@BindingAdapter("android:onEditorAction")
internal fun setOnEditorAction(textView: TextView, onEditorAction: Action) {
    textView.setOnEditorActionListener { _, _, event ->
        if(event?.action != KeyEvent.ACTION_DOWN) {
            return@setOnEditorActionListener false
        }

        onEditorAction.run()

        true
    }
}

@BindingAdapter("android:selected")
internal fun setSelected(view: View, selected: Boolean) {
    view.isSelected = selected
}

@BindingAdapter("toolbarResources")
internal fun setToolbarResources(view: Toolbar, toolbarResources: ToolbarResources?) {
    toolbarResources?.let {
        view.setNavigationIcon(it.navigationRes)
    }
}

@BindingAdapter("lottie_fileName")
internal fun setLottieFilename(view: LottieAnimationView, fileName: String) {
    view.setAnimation(fileName)
}