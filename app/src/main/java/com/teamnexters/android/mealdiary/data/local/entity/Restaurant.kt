package com.teamnexters.android.mealdiary.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant")
internal data class Restaurant(
        @PrimaryKey
        @ColumnInfo(name = "restaurant_id")
        val id: Long,

        val restaurantName: String,
        val restaurantAddress: String,
        val latitude: Long,
        val longitude: Long
)