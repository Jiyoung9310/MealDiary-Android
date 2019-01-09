package com.teamnexters.android.mealdiary.repository

import com.teamnexters.android.mealdiary.data.local.entity.Diary
import io.reactivex.Completable
import io.reactivex.Flowable

internal interface LocalRepository {
    fun diary(id: Long): Flowable<Diary>
    fun diaries(): Flowable<List<Diary>>
    fun upsertDiaries(vararg diaries: Diary): Completable
    fun deleteDiaries(vararg diaries: Diary): Completable
    fun deleteDiary(id: Long): Completable
}