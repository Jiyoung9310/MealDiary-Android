package com.teamnexters.android.mealdiary.ui.write.note

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.withLatestFromSecond
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable

internal interface NoteViewModel {
    interface Inputs {
        fun toClickNext()
        fun toNavigate(screen: Screen)
    }

    interface Outputs {
        fun ofClickNext(): Observable<Unit>
        fun ofNavigate(): Observable<Screen>
    }

    class ViewModel(
            private val schedulerProvider: SchedulerProvider

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val title = MutableLiveData<String>().apply { postValue("") }
        val content = MutableLiveData<String>()

        private val clickNextRelay = PublishRelay.create<Unit>()
        private val navigateRelay = PublishRelay.create<Screen>()

        init {
            disposables.addAll(
                    outputs.ofClickNext()
                            .withLatestFromSecond(ofScreen<Screen.Write.Note>())
                            .map {
                                it.writeParam.apply {
                                    this.title = this@ViewModel.title.value
                                    this.content = this@ViewModel.content.value
                                }
                            }
                            .subscribeOf(onNext = { inputs.toNavigate(Screen.Write.Photo(it)) })
            )
        }

        override fun toClickNext() = clickNextRelay.accept(Unit)
        override fun ofClickNext(): Observable<Unit> = clickNextRelay

        override fun toNavigate(screen: Screen) = navigateRelay.accept(screen)
        override fun ofNavigate(): Observable<Screen> = navigateRelay
    }
}