package com.teamnexters.android.mealdiary.ui.write.score

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.withLatestFromSecond
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable
import org.threeten.bp.ZonedDateTime

internal interface ScoreViewModel {
    interface Inputs {
        fun toClickComplete()
        fun toFinish()
    }

    interface Outputs {
        fun ofClickComplete(): Observable<Unit>
        fun ofFinish(): Observable<Unit>
    }

    class ViewModel(
            private val schedulerProvider: SchedulerProvider,
            private val localRepository: LocalRepository

    ) : BaseViewModel(), Inputs, Outputs {

        val scoreItems = MutableLiveData<List<ScoreItem>>()
        val scoreProgress = MutableLiveData<Int>().apply { postValue(0) }

        val inputs: Inputs = this
        val outputs: Outputs = this

        private val clickCompleteRelay = PublishRelay.create<Unit>()
        private val finishRelay = PublishRelay.create<Unit>()

        init {
            disposables.addAll(
                    localRepository.scoreItems()
                            .subscribeOf(onNext = { scoreItems.postValue(it) }),

                    outputs.ofClickComplete()
                            .withLatestFromSecond(ofScreen<Screen.Write.Score>())
                            .doOnNext {
                                val param = it.writeParam
                                val scoreItem = scoreItems.value!![scoreProgress.value!! / 10]

                                val diary = Diary(
                                        title = param.title ?: "",
                                        content = param.content,
                                        hashTags = param.hashTags,
                                        score = scoreItem.score,
                                        photoUrls = param.photoUrls,
                                        restaurant = param.restaurant,
                                        date = ZonedDateTime.now()
                                )

                                localRepository.upsertDiaries(diary).subscribe()
                            }
                            .subscribeOf(onNext = { inputs.toFinish() })
            )
        }

        override fun toClickComplete() = clickCompleteRelay.accept(Unit)
        override fun ofClickComplete(): Observable<Unit> = clickCompleteRelay

        override fun toFinish() = finishRelay.accept(Unit)
        override fun ofFinish(): Observable<Unit> = finishRelay
    }
}