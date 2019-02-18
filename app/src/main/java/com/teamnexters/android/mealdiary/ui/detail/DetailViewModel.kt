package com.teamnexters.android.mealdiary.ui.detail

import androidx.lifecycle.LiveData
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.toLiveData
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider

internal interface DetailViewModel {
    interface Inputs {
    }

    interface Outputs {
    }

    class ViewModel(
            private val schedulerProvider: SchedulerProvider,
            private val localRepository: LocalRepository) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val diaryItem: LiveData<ListItem.DiaryItem> = ofScreen<Screen.Detail>()
                .map { it.diaryId }
                .switchMap {
                    localRepository.diary(it).toObservable()
                }
                .map { ListItem.DiaryItem(it) }
                .toLiveData()

        init {
            disposables.addAll(

                    
            )
        }

    }
}