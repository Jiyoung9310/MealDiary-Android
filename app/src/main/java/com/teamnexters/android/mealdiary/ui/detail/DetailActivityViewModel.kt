package com.teamnexters.android.mealdiary.ui.detail

import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider

internal interface DetailActivityViewModel {
    interface Inputs {
    }

    interface Outputs {
    }

    class ViewModel(private val schedulerProvider: SchedulerProvider) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this


        init {
        }

    }
}