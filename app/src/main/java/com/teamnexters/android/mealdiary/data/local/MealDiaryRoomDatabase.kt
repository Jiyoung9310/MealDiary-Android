package com.teamnexters.android.mealdiary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.teamnexters.android.mealdiary.data.local.converter.HashTagConverter
import com.teamnexters.android.mealdiary.data.local.converter.ListConverter
import com.teamnexters.android.mealdiary.data.local.converter.RestaurantConverter
import com.teamnexters.android.mealdiary.data.local.converter.ZonedDateTimeConverter
import com.teamnexters.android.mealdiary.data.local.dao.*
import com.teamnexters.android.mealdiary.data.local.entity.*

@Database(
        entities = [
            Diary::class,
            Restaurant::class,
            HashTag::class
        ],
        version = 3,
        exportSchema = false
)
@TypeConverters(
        ListConverter::class,
        RestaurantConverter::class,
        HashTagConverter::class,
        ZonedDateTimeConverter::class
)
internal abstract class MealDiaryRoomDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
    abstract fun hashTagDao(): HashTagDao
    abstract fun restaurantDao(): RestaurantDao
}