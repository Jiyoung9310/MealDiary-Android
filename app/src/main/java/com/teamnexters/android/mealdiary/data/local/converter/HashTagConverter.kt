package com.teamnexters.android.mealdiary.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.teamnexters.android.mealdiary.data.local.entity.HashTag

internal class HashTagConverter {

    private val gson = GsonProvider.gson

    @TypeConverter
    fun fromHashTagList(value: List<HashTag>): String {
        val type = object : TypeToken<List<HashTag>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStringList(value: String): List<HashTag> {
        val type = object : TypeToken<List<HashTag>>() {}.type
        return gson.fromJson(value, type)
    }

}