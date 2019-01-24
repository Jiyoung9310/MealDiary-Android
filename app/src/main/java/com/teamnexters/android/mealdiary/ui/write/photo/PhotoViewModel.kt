package com.teamnexters.android.mealdiary.ui.write.photo

import com.teamnexters.android.mealdiary.base.BaseViewModel

internal interface PhotoViewModel {
    interface Inputs {

    }

    interface Outputs {

    }

    class ViewModel() : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {

        }

    }
}