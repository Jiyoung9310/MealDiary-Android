package com.teamnexters.android.mealdiary.data.local.dao

import androidx.room.*
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import io.reactivex.Observable

@Dao
internal interface DiaryDao {
    @Query("SELECT * FROM diary")
    fun diaries(): Observable<List<Diary>>

    @Query("SELECT * FROM diary WHERE id = :id")
    fun diary(id: Int): Observable<Diary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertDiaries(diaries: List<Diary>)

    @Delete
    fun deleteDiaries(diaries: List<Diary>)

    @Query("DELETE FROM diary")
    fun deleteAllDiaries()
}