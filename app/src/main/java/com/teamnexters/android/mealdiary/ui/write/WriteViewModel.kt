package com.teamnexters.android.mealdiary.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.ui.ToolbarChannel
import com.teamnexters.android.mealdiary.ui.ToolbarResources
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.toLiveData
import io.reactivex.Observable

internal interface WriteViewModel {
    interface Inputs {
        fun toProgress(progress: Int)
    }

    interface Outputs {
        fun ofProgress(): Observable<Int>
    }

    class ViewModel(
            private val toolbarChannel: ToolbarChannel

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outpus: Outputs = this

        val toolbarResources: LiveData<ToolbarResources> = toolbarChannel.toolbarRelay().toLiveData()
        val progress = MutableLiveData<Int>().apply { postValue(33) }

        private val progressRelay = BehaviorRelay.createDefault(33)

        init {
            disposables.addAll(
                    outpus.ofProgress()
                            .subscribeOf(onNext = { progress.postValue(it) })
            )
        }

        override fun toProgress(progress: Int) = progressRelay.accept(progress)
        override fun ofProgress(): Observable<Int> = progressRelay
    }

}