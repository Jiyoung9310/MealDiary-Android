package com.teamnexters.android.mealdiary.ui.write

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.data.local.entity.HashTag
import com.teamnexters.android.mealdiary.data.local.entity.Restaurant
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import com.teamnexters.android.mealdiary.util.extension.withLatestFromSecond
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import org.threeten.bp.ZonedDateTime

internal interface WriteViewModel {
    interface Inputs {
        fun toClickWrite()
        fun toContent(content: String)
        fun toNavigateToMain()
    }

    interface Outputs {
        fun ofClickWrite(): Observable<Unit>
        fun ofContent(): Observable<String>
        fun ofNavigateToMain(): Observable<Unit>
    }

    class ViewModel(
            schedulerProvider: SchedulerProvider,
            localRepository: LocalRepository

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val content = MutableLiveData<String>()

        private val clickWriteRelay = PublishRelay.create<Unit>()
        private val navigateToMainRelay = PublishRelay.create<Unit>()

        private val contentRelay = BehaviorRelay.create<String>()


        private val loadedDiary = ofScreen<Screen.Write.Modify>().switchMap { localRepository.diary(it.diaryId).toObservable() }

        init {
            disposables.addAll(
                    loadedDiary
                            .subscribeOf(onNext = { inputs.toContent(it.content) }),

                    outputs.ofContent()
                            .observeOn(schedulerProvider.ui())
                            .subscribeOf(onNext = content::setValue),

                    outputs.ofClickWrite()
                            .withLatestFromSecond(ofScreen<Screen.Write.Write>())
                            .throttleClick()
                            .withLatestFromSecond(ofContent())
                            .map { content ->
                                getDummy().copy(content = content)
                            }
                            .doOnNext {
                                localRepository.upsertDiaries(it)
                                        .subscribeOf()
                            }
                            .subscribeOf(onNext = { inputs.toNavigateToMain() }),

                    outputs.ofClickWrite()
                            .withLatestFromSecond(ofScreen<Screen.Write.Modify>())
                            .throttleClick()
                            .withLatestFrom(loadedDiary, ofContent()) { _, diary, content ->
                                diary.copy(content = content)
                            }
                            .doOnNext {
                                localRepository.upsertDiaries(it)
                                        .subscribeOf()
                            }
                            .subscribeOf(onNext = { inputs.toNavigateToMain() })
            )
        }

        override fun toClickWrite() = clickWriteRelay.accept(Unit)
        override fun toContent(content: String) = contentRelay.accept(content)
        override fun toNavigateToMain() = navigateToMainRelay.accept(Unit)

        override fun ofClickWrite(): Observable<Unit> = clickWriteRelay
        override fun ofContent(): Observable<String> = contentRelay
        override fun ofNavigateToMain(): Observable<Unit> = navigateToMainRelay

        private fun getDummy(): Diary {
            return Diary(
                    content = "안뇽",
                    score = 20,
                    photoUrls = listOf("photo1", "photo2"),
                    restaurant = Restaurant(name = "재환식당", address = "방배동"),
                    hashTags = listOf(HashTag("#해쉬브라운")),
                    date = ZonedDateTime.now()
            )
        }
    }

}