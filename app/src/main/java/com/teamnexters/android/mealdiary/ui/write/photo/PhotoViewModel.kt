package com.teamnexters.android.mealdiary.ui.write.photo

import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable

internal interface PhotoViewModel {
    interface Inputs {
    }

    interface Outputs {
        fun photoList(): Observable<List<Photo>>
    }

    class ViewModel(
            private val schedulerProvider: SchedulerProvider,
            private val galleryProvider: GalleryProvider

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {

        }

        override fun photoList(): Observable<List<Photo>> {
            return Observable.fromCallable { galleryProvider.photoPathList() }.subscribeOn(schedulerProvider.io())
        }

    }

}