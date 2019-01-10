package com.teamnexters.android.mealdiary.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

internal abstract class BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upserts(vararg entities: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsert(entity: T)

    @Delete
    abstract fun delete(entity: T)

    @Delete
    abstract fun deletes(vararg entities: T)
}