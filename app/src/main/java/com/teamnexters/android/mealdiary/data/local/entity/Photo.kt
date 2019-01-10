package com.teamnexters.android.mealdiary.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
internal data class Photo(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val diaryId: Long,
        val photoUrl: String
)