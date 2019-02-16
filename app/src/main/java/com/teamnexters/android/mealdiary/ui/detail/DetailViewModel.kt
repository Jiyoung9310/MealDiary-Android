package com.teamnexters.android.mealdiary.ui.detail

import androidx.lifecycle.LiveData
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.util.extension.toLiveData
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider

internal interface DetailViewModel {
    interface Inputs {

    }

    interface Outputs {

    }

    class ViewModel(
            schedulerProvider: SchedulerProvider,
            localRepository: LocalRepository) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val diaryItems: LiveData<List<ListItem.DiaryItem>> = localRepository.diary().map { diaries ->
            diaries.map { ListItem.DiaryItem(it) }
        }.toLiveData()

        init {

        }

    }
}