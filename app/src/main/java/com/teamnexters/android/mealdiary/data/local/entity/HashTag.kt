package com.teamnexters.android.mealdiary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "hashtag")
internal data class HashTag(
        val tagName: String,

        @PrimaryKey
        val id: String = tagName
) : Serializable