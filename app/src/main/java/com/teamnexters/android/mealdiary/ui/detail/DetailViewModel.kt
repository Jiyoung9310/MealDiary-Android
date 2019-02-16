package com.teamnexters.android.mealdiary.ui.detail

import com.teamnexters.android.mealdiary.base.BaseViewModel

internal interface DetailViewModel {
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