package com.teamnexters.android.mealdiary.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.teamnexters.android.mealdiary.data.local.LocalDataSource
import com.teamnexters.android.mealdiary.data.local.LocalDataSourceImpl
import com.teamnexters.android.mealdiary.data.local.MealDiaryRoomDatabase
import org.koin.dsl.module.module

val dbModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(get()) as SharedPreferences }

    single { createRoomDatabase(get(), MealDiaryRoomDatabase::class.java, "meal-diary-db") }

    single { LocalDataSourceImpl(get(), get()) as LocalDataSource }
}

internal inline fun <reified T : RoomDatabase> createRoomDatabase(context: Context, clazz: Class<T>, dbName: String): T {
    return Room.databaseBuilder(context, clazz, dbName)
            .fallbackToDestructiveMigration()
            .build()
}