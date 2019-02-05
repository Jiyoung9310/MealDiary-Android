package com.teamnexters.android.mealdiary.ui.intro

import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import io.reactivex.Observable

internal interface IntroViewModel {
    interface Inputs {
        fun toClickAgree()
        fun toNavigate()
    }

    interface Outputs {
        fun ofClickAgree(): Observable<Unit>
        fun ofNavigate(): Observable<Unit>
    }

    class ViewModel(private val local: LocalRepository) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        private val clickAgreeRelay = PublishRelay.create<Unit>()
        private val navigationToBoardingRelay = PublishRelay.create<Unit>()

        init {
            disposables.addAll(
                    outputs.ofClickAgree()
                            .throttleClick()
                            .subscribeOf(onNext = {
                                local.setPrivacyAgree(true)
                                        .subscribeOf()
                                inputs.toNavigate()
                            })
            )
        }

        override fun toClickAgree() = clickAgreeRelay.accept(Unit)
        override fun toNavigate() = navigationToBoardingRelay.accept(Unit)

        override fun ofClickAgree(): Observable<Unit> = clickAgreeRelay
        override fun ofNavigate(): Observable<Unit> = navigationToBoardingRelay
    }
}