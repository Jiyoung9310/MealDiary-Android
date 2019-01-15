package com.teamnexters.android.mealdiary.data.local.converter

import com.google.gson.GsonBuilder

internal object GsonProvider {
    val gson = GsonBuilder()
            .create()
}
