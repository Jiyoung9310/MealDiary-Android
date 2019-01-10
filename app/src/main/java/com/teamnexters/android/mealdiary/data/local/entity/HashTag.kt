package com.teamnexters.android.mealdiary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hashtag")
internal data class HashTag(
        @PrimaryKey
        val id: Long,
        val diaryId: Long,
        val tagName: String
)