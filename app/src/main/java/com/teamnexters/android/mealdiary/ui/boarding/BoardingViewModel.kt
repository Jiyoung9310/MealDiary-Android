package com.teamnexters.android.mealdiary.ui.boarding

import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider

internal interface BoardingViewModel {
    interface Inputs {

    }

    interface Outputs {

    }

    class ViewModel(schedulerProvider: SchedulerProvider) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {

        }

    }
}