package com.teamnexters.android.mealdiary.ui.boarding

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable

internal interface BoardingViewModel {
    interface Inputs {
        fun toClickNext()
        fun toClickSkip()
        fun toCheckPosition(pos: Int)
        fun toNavigate()
    }

    interface Outputs {
        fun ofClickNext(): Observable<Unit>
        fun ofClickSkip(): Observable<Unit>
        fun ofCheckPosition(): Observable<Int>
        fun ofNavigate(): Observable<Unit>
    }

    class ViewModel(private val schedulerProvider: SchedulerProvider,
                    private val localRepository: LocalRepository) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val boardItems = MutableLiveData<List<BoardItem>>()
        val boardPosition = MutableLiveData<Int>()

        private val clickNextRelay = PublishRelay.create<Unit>()
        private val clickSkipRelay = PublishRelay.create<Unit>()
        private val checkPositionRelay = PublishRelay.create<Int>()
        private val navigationToMinRelay = PublishRelay.create<Unit>()

        init {
            disposables.addAll(
                    localRepository.boardItems()
                            .subscribeOf(onNext = {
                                boardItems.postValue(it)
                                boardPosition.postValue(0)
                                checkPositionRelay.accept(it.size)
                            }),

                    outputs.ofClickSkip()
                            .throttleClick()
                            .subscribeOf(onNext = {
                                inputs.toNavigate()
                            })
                    )

        }

        override fun toClickNext() = clickNextRelay.accept(Unit)
        override fun toClickSkip() = clickSkipRelay.accept(Unit)
        override fun toCheckPosition(pos: Int) = checkPositionRelay.accept(pos)
        override fun toNavigate() = navigationToMinRelay.accept(Unit)

        override fun ofClickNext(): Observable<Unit> = clickNextRelay
        override fun ofClickSkip(): Observable<Unit> = clickSkipRelay
        override fun ofCheckPosition(): Observable<Int> = checkPositionRelay
        override fun ofNavigate(): Observable<Unit> = navigationToMinRelay

    }
}