package com.teamnexters.android.mealdiary.ui.detail

import androidx.lifecycle.MutableLiveData
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
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

        val diary = MutableLiveData<Diary>()

        init {
            disposables.addAll(
                    ofScreen<Screen.Detail>()
                            .map { it.diaryId }
                            .switchMap {
                                localRepository.diary(it).toObservable()
                            }
                            .subscribeOf(onNext = { diary.postValue(it) })

            )
        }

    }
}