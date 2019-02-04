package com.teamnexters.android.mealdiary.ui.write.note

import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider

internal interface NoteViewModel {
    interface Inputs {

    }

    interface Outputs {

    }

    class ViewModel(
            private val schedulerProvider: SchedulerProvider

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {

        }

    }
}