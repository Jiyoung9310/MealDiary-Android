package com.teamnexters.android.mealdiary.ui.boarding

import androidx.lifecycle.MutableLiveData
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider

internal interface BoardingViewModel {
    interface Inputs {

    }

    interface Outputs {

    }

    class ViewModel(private val schedulerProvider: SchedulerProvider,
                    private val localRepository: LocalRepository) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val boardItems = MutableLiveData<List<BoardItem>>()
        val boardPosition = MutableLiveData<Int>()

        init {
            disposables.addAll(
                    localRepository.boardItems()
                            .subscribeOf(onNext = { boardItems.postValue(it) })
                    )
        }

    }
}