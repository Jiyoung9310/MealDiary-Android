package com.teamnexters.android.mealdiary.data

internal data class TextChangedParam(
        val s: CharSequence,
        val start: Int,
        val before: Int,
        val count: Int
)