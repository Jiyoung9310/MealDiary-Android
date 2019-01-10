package com.teamnexters.android.mealdiary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.teamnexters.android.mealdiary.data.local.converter.ZonedDateTimeConverter
import com.teamnexters.android.mealdiary.data.local.dao.RestaurantDao
import com.teamnexters.android.mealdiary.data.local.dao.DiaryDao
import com.teamnexters.android.mealdiary.data.local.dao.HashTagDao
import com.teamnexters.android.mealdiary.data.local.dao.PhotoDao
import com.teamnexters.android.mealdiary.data.local.entity.Restaurant
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.data.local.entity.HashTag
import com.teamnexters.android.mealdiary.data.local.entity.Photo

@Database(
        entities = [
            Diary::class,
            Restaurant::class,
            HashTag::class,
            Photo::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(
        ZonedDateTimeConverter::class
)
internal abstract class MealDiaryRoomDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
    abstract fun hashTagDao(): HashTagDao
    abstract fun photoDao(): PhotoDao
    abstract fun restaurantDao(): RestaurantDao
}