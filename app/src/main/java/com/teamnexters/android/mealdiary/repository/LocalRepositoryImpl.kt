package com.teamnexters.android.mealdiary.repository

import com.teamnexters.android.mealdiary.data.DataSource
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import io.reactivex.Completable
import io.reactivex.Flowable

internal class LocalRepositoryImpl(private val dataSource: DataSource) : LocalRepository {
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
}