package com.teamnexters.android.mealdiary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary")
internal data class Diary(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val content: String
)