package com.teamnexters.android.mealdiary.repository

import com.teamnexters.android.mealdiary.data.local.entity.Diary
import io.reactivex.Completable
import io.reactivex.Observable

internal interface LocalRepository {
    fun diaries(): Observable<List<Diary>>
    fun upsertDiaries(diaries: List<Diary>): Completable
}