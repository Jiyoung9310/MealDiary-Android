package com.teamnexters.android.mealdiary.data.local

import com.teamnexters.android.mealdiary.data.local.entity.Diary
import io.reactivex.Completable
import io.reactivex.Flowable

internal interface LocalDataSource {
    fun diaries(): Flowable<List<Diary>>
    fun diary(id: Long): Flowable<Diary>
    fun deleteDiary(id: Long): Completable
    fun upsertDiaries(vararg diaries: Diary): Completable
    fun deleteDiaries(vararg diaries: Diary): Completable
    fun deleteAllDiaries(): Completable
}