package com.teamnexters.android.mealdiary.data.local.converter

import androidx.room.TypeConverter
import com.teamnexters.android.mealdiary.data.local.entity.Restaurant

internal class RestaurantConverter {

    private val gson = GsonProvider.gson

    @TypeConverter
    fun fromRestaurant(value: Restaurant?): String {
        return gson.toJson(value, Restaurant::class.java)
    }

    @TypeConverter
    fun toRestaurant(value: String): Restaurant? {
        return gson.fromJson(value, Restaurant::class.java)
    }

}