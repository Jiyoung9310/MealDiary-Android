package com.teamnexters.android.mealdiary.repository

import android.content.res.Resources
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.data.local.LocalDataSource
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.ui.write.score.ScoreItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

internal class LocalRepositoryImpl(
        private val dataSource: LocalDataSource,
        private val resources: Resources

) : LocalRepository {
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

    override fun scoreItems(): Observable<List<ScoreItem>> {
        val scoreTextArray = resources.getStringArray(R.array.array_score_text)

        return Observable.fromCallable {
            (0..10).map {
                val score = it * 10

                val title = when(score) {
                    in 0..29 -> scoreTextArray[0]
                    in 30..49 -> scoreTextArray[1]
                    in 50..69 -> scoreTextArray[2]
                    in 70..89 -> scoreTextArray[3]
                    in 90..100 -> scoreTextArray[4]
                    else -> throw RuntimeException("invalid score = $score")
                }

                ScoreItem(
                        score = it * 10,
                        title = title
                )
            }
        }
    }
}