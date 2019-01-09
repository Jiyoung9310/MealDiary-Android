package com.teamnexters.android.mealdiary.ui.write

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.base.LifecycleState
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.withLatestFrom

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
                    ofScreen<Screen.Write.Modify>()
                            .switchMap { localRepository.diary(it.id).toObservable() }
                            .subscribeOf(onNext = { inputs.toContent(it.content) }),

                    outputs.ofContent()
                            .observeOn(schedulerProvider.ui())
                            .subscribeOf(onNext = content::setValue),

                    outputs.ofClickWrite()
                            .throttleClick()
                            .withLatestFrom(ofScreen<Screen.Write>(), ofContent())
                            .map { (_, screen, content) ->
                                when(screen) {
                                    is Screen.Write.Write -> Diary(content = content)
                                    is Screen.Write.Modify -> Diary(id = screen.id, content = content)
                                }
                            }
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