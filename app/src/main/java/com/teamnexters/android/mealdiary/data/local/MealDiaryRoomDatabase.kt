package com.teamnexters.android.mealdiary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.teamnexters.android.mealdiary.data.local.converter.ZonedDateTimeConverter
import com.teamnexters.android.mealdiary.data.local.dao.DiaryDao
import com.teamnexters.android.mealdiary.data.local.entity.Diary

@Database(
        entities = [
            Diary::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(
        ZonedDateTimeConverter::class
)
internal abstract class MealDiaryRoomDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
}