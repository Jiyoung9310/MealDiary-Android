package com.teamnexters.android.mealdiary.data.local.dao

import androidx.room.*
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import io.reactivex.Flowable

@Dao
internal interface DiaryDao {
    @Query("SELECT * FROM diary")
    fun diaries(): Flowable<List<Diary>>

    @Query("SELECT * FROM diary WHERE id = :id")
    fun diary(id: Int): Flowable<Diary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertDiaries(vararg diaries: Diary)

    @Delete
    fun deleteDiaries(vararg diaries: Diary)

    @Query("DELETE FROM diary")
    fun deleteAllDiaries()
}