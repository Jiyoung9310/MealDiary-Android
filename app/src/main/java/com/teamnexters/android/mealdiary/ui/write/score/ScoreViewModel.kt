package com.teamnexters.android.mealdiary.ui.write.score

import androidx.lifecycle.MutableLiveData
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider

internal interface ScoreViewModel {
    interface Inputs {

    }

    interface Outputs {

    }

    class ViewModel(
            private val schedulerProvider: SchedulerProvider

    ) : BaseViewModel(), Inputs, Outputs {

        val scoreItems = MutableLiveData<List<ScoreItem>>()

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {
            val items = (0..5).map { ScoreItem(it * 10, "title$it") }

            scoreItems.postValue(items)
        }

    }
}