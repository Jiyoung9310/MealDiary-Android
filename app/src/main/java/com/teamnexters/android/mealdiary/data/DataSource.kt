package com.teamnexters.android.mealdiary.data

import com.teamnexters.android.mealdiary.data.local.entity.Diary
import io.reactivex.Completable
import io.reactivex.Observable

internal interface DataSource {
    fun diaries(): Observable<List<Diary>>
    fun diary(id: Int): Observable<Diary>
    fun upsertDiaries(diaries: List<Diary>): Completable
    fun deleteDiaries(diaries: List<Diary>): Completable
    fun deleteAllDiaries(): Completable
}