package com.teamnexters.android.mealdiary.ui.detail.fragment

import androidx.lifecycle.MutableLiveData
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf

internal class DetailViewModel {
    interface Inputs {

    }

    interface Outputs

    class ViewModel(
            private val localRepository: LocalRepository
    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val diary = MutableLiveData<Diary>()
        val detailPhotoList = MutableLiveData<List<DetailPhoto>>()
        val photoPosition = MutableLiveData<Int>()

        init {
            disposables.addAll(
                    ofScreen<Screen.Detail>()
                            .map { it.diaryId }
                            .switchMap {
                                localRepository.diary(it).toObservable()
                            }
                            .subscribeOf(onNext = {
                                diary.postValue(it)
                                detailPhotoList.postValue(it.photoUrls.map { photoUrl -> DetailPhoto(photoUrl) })
                                photoPosition.postValue(1)
                            })

            )
        }
    }
}