package com.teamnexters.android.mealdiary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant")
internal data class Restaurant(
        val placeName: String,
        val addressName: String,
        val x: Double = .0,
        val y: Double = .0,

        @PrimaryKey
        val id: String = placeName + addressName
)