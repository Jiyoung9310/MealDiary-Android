package com.teamnexters.android.mealdiary.ui.write.photo

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.withLatestFromSecond
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable

internal interface PhotoViewModel {
    interface Inputs {
        fun toClickNext()
        fun toNavigate(screen: Screen)
        fun toSelectedPhotoList(photos: List<Photo>)
    }

    interface Outputs {
        fun ofPhotoList(): Observable<List<Photo>>
        fun ofClickNext(): Observable<Unit>
        fun ofNavigate(): Observable<Screen>
        fun ofSelectedPhotoList(): Observable<List<Photo>>
    }

    class ViewModel(
            private val schedulerProvider: SchedulerProvider,
            private val galleryProvider: GalleryProvider

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        private val clickNextRelay = PublishRelay.create<Unit>()
        private val navigateToRestaurantRelay = PublishRelay.create<Screen>()
        private val selectedPhotoRelay = BehaviorRelay.createDefault<List<Photo>>(listOf())

        init {
            disposables.addAll(
                    outputs.ofClickNext()
                            .withLatestFromSecond(outputs.ofSelectedPhotoList())
                            .subscribeOf(onNext = {

                            })
            )
        }

        override fun ofPhotoList(): Observable<List<Photo>> {
            return Observable.fromCallable { galleryProvider.photoPathList() }.subscribeOn(schedulerProvider.io())
        }

        override fun toClickNext() = clickNextRelay.accept(Unit)
        override fun ofClickNext(): Observable<Unit> = clickNextRelay

        override fun toNavigate(screen: Screen) = navigateToRestaurantRelay.accept(screen)
        override fun ofNavigate(): Observable<Screen> = navigateToRestaurantRelay

        override fun toSelectedPhotoList(photos: List<Photo>) = selectedPhotoRelay.accept(photos)
        override fun ofSelectedPhotoList(): Observable<List<Photo>> = selectedPhotoRelay
    }

}