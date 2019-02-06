package com.teamnexters.android.mealdiary.ui.write.score

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.withLatestFromSecond
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import org.threeten.bp.ZonedDateTime
import java.util.concurrent.TimeUnit

internal interface ScoreViewModel {
    interface Inputs {
        fun toScore(progress: Int)
        fun toClickComplete()
        fun toFinish()
    }

    interface Outputs {
        fun ofScore(): Observable<Int>
        fun ofClickComplete(): Observable<Unit>
        fun ofFinish(): Observable<Unit>
    }

    class ViewModel(
            private val schedulerProvider: SchedulerProvider,
            private val localRepository: LocalRepository

    ) : BaseViewModel(), Inputs, Outputs {

        val scoreItems = MutableLiveData<List<ScoreItem>>()
        val scoreProgress = MutableLiveData<Int>()
        val scoreCardPosition = MutableLiveData<Int>()

        val inputs: Inputs = this
        val outputs: Outputs = this

        private val scoreRelay = BehaviorRelay.createDefault(0)

        private val clickCompleteRelay = PublishRelay.create<Unit>()
        private val finishRelay = PublishRelay.create<Unit>()

        init {
            val items = (0..5).map { ScoreItem(it * 10, "title$it") }

            scoreItems.postValue(items)

            disposables.addAll(
                    outputs.ofScore()
                            .throttleLast(300, TimeUnit.MILLISECONDS)
                            .subscribeOf(onNext = {
                                scoreProgress.postValue(it)
                                scoreCardPosition.postValue(it / 20)
                            }),

                    outputs.ofClickComplete()
                            .withLatestFromSecond(ofScreen<Screen.Write.Score>())
                            .withLatestFrom(outputs.ofScore())
                            .doOnNext {
                                val param = it.first.writeParam
                                val score = it.second

                                val diary = Diary(
                                        title = param.title ?: "",
                                        content = param.content,
                                        score = score,
                                        photoUrls = param.photoUrls,
                                        restaurant = param.restaurant,
                                        hashTags = param.hashTags,
                                        date = ZonedDateTime.now()
                                )

                                localRepository.upsertDiaries(diary).subscribe()
                            }
                            .subscribeOf(onNext = { inputs.toFinish() })
            )
        }

        override fun toScore(progress: Int) = scoreRelay.accept(progress)
        override fun ofScore(): Observable<Int> = scoreRelay

        override fun toClickComplete() = clickCompleteRelay.accept(Unit)
        override fun ofClickComplete(): Observable<Unit> = clickCompleteRelay

        override fun toFinish() = finishRelay.accept(Unit)
        override fun ofFinish(): Observable<Unit> = finishRelay
    }
}