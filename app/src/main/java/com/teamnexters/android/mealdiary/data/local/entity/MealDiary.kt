package com.teamnexters.android.mealdiary.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation


internal data class MealDiary(
    @Embedded
    val diary: Diary,

    @Embedded
    val restaurant: Restaurant?,

    @Relation(parentColumn = "id", entityColumn = "diaryId", entity = HashTag::class)
    val hashTags: List<HashTag> = mutableListOf(),

    @Relation(parentColumn = "id", entityColumn = "diaryId", entity = Photo::class)
    val photos: List<Photo> = mutableListOf()
)
