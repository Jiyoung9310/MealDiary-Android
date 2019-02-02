package com.teamnexters.android.mealdiary.ui.write.restaurant

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.repository.RemoteRepository
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit

internal interface RestaurantViewModel {
    interface Inputs {
        fun toKeyword(keyword: String)
    }

    interface Outputs {
        fun ofKeyword(): Observable<String>
    }

    class ViewModel(
            private val remoteRepository: RemoteRepository

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val keyword: MutableLiveData<String> = MutableLiveData()

        private val keywordRelay = BehaviorRelay.createDefault("")

        init {
            disposables.addAll(
                    outputs.ofKeyword()
                            .doOnNext { keyword.postValue(it) }
                            .debounce(500, TimeUnit.MILLISECONDS)
                            .filter { it.isNotBlank() }
                            .switchMapSingle { remoteRepository.search(it) }
                            .subscribeOf(onNext = {
                                Timber.d("$it")
                            })
            )
        }

        override fun toKeyword(keyword: String) = keywordRelay.accept(keyword)
        override fun ofKeyword(): Observable<String> = keywordRelay

    }
}