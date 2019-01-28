package com.teamnexters.android.mealdiary.ui.write

import androidx.lifecycle.LiveData
import com.jakewharton.rxrelay2.Relay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.ui.ToolbarChannel
import com.teamnexters.android.mealdiary.ui.ToolbarResources
import com.teamnexters.android.mealdiary.util.extension.toLiveData

internal interface WriteViewModel {
    interface Inputs

    interface Outputs

    class ViewModel(
            toolbarChannel: ToolbarChannel

    ) : BaseViewModel(), Inputs, Outputs {

        val toolbarResources: LiveData<ToolbarResources> = toolbarChannel.toolbarRelay().toLiveData()

    }

}