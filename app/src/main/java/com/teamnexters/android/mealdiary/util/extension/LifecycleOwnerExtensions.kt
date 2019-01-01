package com.teamnexters.android.mealdiary.util.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

internal fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, block: (T) -> Unit) = liveData.observe(this, Observer{
    it?.run {
        block(it)
    }
})