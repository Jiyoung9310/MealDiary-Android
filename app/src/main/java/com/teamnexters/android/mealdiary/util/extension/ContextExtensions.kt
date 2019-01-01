package com.teamnexters.android.mealdiary.util.extension

import android.content.Context
import android.widget.Toast

fun Context?.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context?.hasResource(resId: Int): Boolean {
    return try {
        if(this == null) {
            return false
        }

        this.resources.getResourceName(resId)

        true
    } catch (ignored: Throwable) {
        false
    }
}