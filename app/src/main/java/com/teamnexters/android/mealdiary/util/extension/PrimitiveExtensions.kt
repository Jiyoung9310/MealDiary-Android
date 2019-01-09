package com.teamnexters.android.mealdiary.util.extension

import android.content.res.Resources

internal val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

internal val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()