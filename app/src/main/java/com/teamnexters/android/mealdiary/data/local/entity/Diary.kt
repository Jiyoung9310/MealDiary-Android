package com.teamnexters.android.mealdiary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "diary")
internal data class Diary(
        val title: String,
        val content: String?,
        val score: Int,
        val photoUrls: List<String> = mutableListOf(),

        val restaurant: Restaurant?,

        val date: ZonedDateTime,

        @PrimaryKey(autoGenerate = true)
        val id: Long = 0
)