package com.teamnexters.android.mealdiary.ui.write

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import com.teamnexters.android.mealdiary.util.extension.withLatestFromSecond
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable

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

        init {
            disposables.addAll(
                    outputs.ofContent()
                            .observeOn(schedulerProvider.ui())
                            .subscribeOf(onNext = content::setValue),

                    outputs.ofClickWrite()
                            .throttleClick()
                            .withLatestFromSecond(ofContent())
                            .map { Diary(content = it) }
                            .doOnNext { localRepository.upsertDiaries(it).subscribeOf() }
                            .subscribeOf(onNext = { inputs.toNavigateToMain() })
            )
        }

        override fun toClickWrite() = clickWriteRelay.accept(Unit)
        override fun toContent(content: String) = contentRelay.accept(content)
        override fun toNavigateToMain() = navigateToMainRelay.accept(Unit)

        override fun ofClickWrite(): Observable<Unit> = clickWriteRelay
        override fun ofContent(): Observable<String> = contentRelay
        override fun ofNavigateToMain(): Observable<Unit> = navigateToMainRelay
    }

}