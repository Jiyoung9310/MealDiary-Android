package com.teamnexters.android.mealdiary.ui.write

import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider

internal interface WriteViewModel {
    interface Inputs

    interface Outputs

    class ViewModel() : BaseViewModel(), Inputs, Outputs {

    }

}