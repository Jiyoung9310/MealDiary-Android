package com.teamnexters.android.mealdiary.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken

internal class ListConverter {

    private val gson = GsonProvider.gson

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

}