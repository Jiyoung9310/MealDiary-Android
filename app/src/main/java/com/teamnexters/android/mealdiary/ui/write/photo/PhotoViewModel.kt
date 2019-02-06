package com.teamnexters.android.mealdiary.ui.write.photo

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom

internal interface PhotoViewModel {
    interface Inputs {
        fun toPhotoList(photos: List<Photo>)
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
        private val photosRelay = BehaviorRelay.createDefault<List<Photo>>(emptyList())
        private val selectedPhotoRelay = BehaviorRelay.createDefault<List<Photo>>(emptyList())

        init {
            disposables.addAll(
                    Observable.fromCallable { galleryProvider.photoPathList() }
                            .subscribeOn(schedulerProvider.io())
                            .subscribeOf(onNext = { inputs.toPhotoList(it) }),

                    outputs.ofClickNext()
                            .withLatestFrom(ofScreen<Screen.Write.Photo>(), outputs.ofSelectedPhotoList()) { _, screen, photos ->
                                screen.writeParam.apply {
                                    photoUrls = photos.map { it.imgPath }
                                }
                            }
                            .subscribeOf(onNext = { inputs.toNavigate(Screen.Write.Score(it)) })
            )
        }

        override fun toPhotoList(photos: List<Photo>) = photosRelay.accept(photos)
        override fun ofPhotoList(): Observable<List<Photo>> = photosRelay

        override fun toClickNext() = clickNextRelay.accept(Unit)
        override fun ofClickNext(): Observable<Unit> = clickNextRelay

        override fun toNavigate(screen: Screen) = navigateToRestaurantRelay.accept(screen)
        override fun ofNavigate(): Observable<Screen> = navigateToRestaurantRelay

        override fun toSelectedPhotoList(photos: List<Photo>) = selectedPhotoRelay.accept(photos)
        override fun ofSelectedPhotoList(): Observable<List<Photo>> = selectedPhotoRelay
    }

}