package com.teamnexters.android.mealdiary.ui.write.photo

import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import io.reactivex.Observable

internal interface PhotoViewModel {
    interface Inputs {
        fun onPermissionGranted()
    }

    interface Outputs {
        fun photoList(): Observable<List<Photo>>
        fun permissionGranted(): Observable<Unit>
    }

    class ViewModel(
            private val galleryProvider: GalleryProvider

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        private val permissionGrantedRelay = PublishRelay.create<Unit>()

        init {

        }

        override fun onPermissionGranted() = permissionGrantedRelay.accept(Unit)

        override fun permissionGranted(): Observable<Unit> = permissionGrantedRelay

        override fun photoList(): Observable<List<Photo>> {
            return permissionGrantedRelay.switchMap {
                Observable.fromCallable { galleryProvider.photoPathList() }
            }
        }

    }

}