package com.teamnexters.android.mealdiary.repository

import android.content.res.Resources
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.data.local.LocalDataSource
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.ui.boarding.BoardItem
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
        val scoreLottieFilePathArray = resources.getStringArray(R.array.array_score_lottie_file_name)

        return Observable.fromCallable {
            (0..10).map {
                val score = it * 10

                when(score) {
                    in 0..19 -> ScoreItem(score = score, title = scoreTextArray[0], lottieFilePath = scoreLottieFilePathArray[0])
                    in 20..39 -> ScoreItem(score = score, title = scoreTextArray[1], lottieFilePath = scoreLottieFilePathArray[1])
                    in 40..59 -> ScoreItem(score = score, title = scoreTextArray[2], lottieFilePath = scoreLottieFilePathArray[2])
                    in 60..79 -> ScoreItem(score = score, title = scoreTextArray[3], lottieFilePath = scoreLottieFilePathArray[3])
                    in 80..99 -> ScoreItem(score = score, title = scoreTextArray[4], lottieFilePath = scoreLottieFilePathArray[4])
                    100 -> ScoreItem(score = score, title = scoreTextArray[5], lottieFilePath = scoreLottieFilePathArray[5])
                    else -> throw RuntimeException("invalid score = $score")
                }
            }
        }
    }

    override fun boardItems(): Observable<List<BoardItem>> {
        val boardTitle = resources.getStringArray(R.array.array_board_title)
        val boardSubTitle = resources.getStringArray(R.array.array_board_subtitle)
        val boardImages = resources.obtainTypedArray(R.array.array_board_image)

        return Observable.fromCallable {
            (0 until 3).map {
                BoardItem(
                        title = boardTitle[it],
                        subtitle = boardSubTitle[it],
                        boardImgId = boardImages.getResourceId(it, -1)
                )
            }
        }
    }
}