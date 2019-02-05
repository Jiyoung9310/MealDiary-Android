package com.teamnexters.android.mealdiary.repository

import com.teamnexters.android.mealdiary.data.local.LocalDataSource
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

internal class LocalRepositoryImpl(private val dataSource: LocalDataSource) : LocalRepository {
    override fun diaries(): Flowable<List<Diary>> {
        return dataSource.diaries()
    }

    override fun diary(id: Long): Flowable<Diary> {
        return dataSource.diary(id)
    }

    override fun upsertDiaries(vararg diaries: Diary): Completable {
        return dataSource.upsertDiaries(*diaries)
    }

    override fun deleteDiaries(vararg diaries: Diary): Completable {
        return dataSource.deleteDiaries(*diaries)
    }

    override fun deleteDiary(diary: Diary): Completable {
        return dataSource.deleteDiaries(diary)
    }

    override fun setPrivacyAgree(agree: Boolean): Completable {
        return dataSource.setPrivacyAgree(agree)
    }

    override fun getPrivacyAgree(): Observable<Boolean> {
        return dataSource.getPrivacyAgree()
    }
}