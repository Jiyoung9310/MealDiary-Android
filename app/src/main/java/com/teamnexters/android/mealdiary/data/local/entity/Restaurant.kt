package com.teamnexters.android.mealdiary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant")
internal data class Restaurant(
        val name: String,
        val address: String,
        val latitude: Long = 0L,
        val longitude: Long = 0L,

        @PrimaryKey
        val id: String = name + address
)