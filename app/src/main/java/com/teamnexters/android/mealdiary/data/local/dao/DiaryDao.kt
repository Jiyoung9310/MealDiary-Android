package com.teamnexters.android.mealdiary.data.local.dao

import androidx.room.*
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.data.local.entity.MealDiary
import io.reactivex.Flowable

@Dao
internal abstract class DiaryDao : BaseDao<Diary>() {
    @Query("SELECT * FROM diary")
    abstract fun get(): Flowable<List<Diary>>

    @Query("SELECT * FROM diary")
    abstract fun gets(): Flowable<List<MealDiary>>

    @Query("SELECT * FROM diary WHERE id = :id")
    abstract fun getById(id: Long): Flowable<Diary>

    @Query("DELETE FROM diary WHERE id = :id")
    abstract fun deleteById(id: Long)

    @Query("DELETE FROM diary")
    abstract fun deleteAll()
}