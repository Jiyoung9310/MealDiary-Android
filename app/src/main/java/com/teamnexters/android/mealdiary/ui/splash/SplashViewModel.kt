package com.teamnexters.android.mealdiary.ui.splash

import com.jakewharton.rxrelay2.BehaviorRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import io.reactivex.Observable

internal interface SplashViewModel {
    interface Inputs {
        fun toNavigate(value: Boolean)
    }

    interface Outputs {
        fun ofNavigate(): Observable<Boolean>
    }

    class ViewModel(private val local: LocalRepository) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        private val navigationToIntroRelay = BehaviorRelay.createDefault(false)

        init {
            disposables.addAll(
                    local.getPrivacyAgree()
                            //.delay(2, TimeUnit.SECONDS)
                            .filter { it }
                            .subscribeOf(onNext = {
                                inputs.toNavigate(it)
                            })
            )
        }

        override fun toNavigate(value: Boolean) = navigationToIntroRelay.accept(value)
        override fun ofNavigate(): Observable<Boolean> = navigationToIntroRelay
    }
}