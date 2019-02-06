package com.teamnexters.android.mealdiary.repository

import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.ui.write.score.ScoreItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

internal interface LocalRepository {
    fun diary(id: Long): Flowable<Diary>
    fun diaries(): Flowable<List<Diary>>
    fun upsertDiaries(vararg diaries: Diary): Completable
    fun deleteDiaries(vararg diaries: Diary): Completable
    fun deleteDiary(diary: Diary): Completable
    fun setPrivacyAgree(agree: Boolean): Completable
    fun getPrivacyAgree(): Observable<Boolean>

    fun scoreItems(): Observable<List<ScoreItem>>
}