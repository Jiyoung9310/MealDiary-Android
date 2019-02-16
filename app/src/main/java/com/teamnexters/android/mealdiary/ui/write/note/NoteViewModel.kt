package com.teamnexters.android.mealdiary.ui.write.note

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.repository.RemoteRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import java.util.concurrent.TimeUnit

internal interface NoteViewModel {
    interface Inputs {
        fun toClickNext()
        fun toSearch(keyword: String)
        fun toClickRestaurantItem(restaurantItem: RestaurantItem)
        fun toNavigate(screen: Screen)
    }

    interface Outputs {
        fun ofClickNext(): Observable<Unit>
        fun ofSearch(): Observable<String>
        fun ofRestaurantClickItem(): Observable<RestaurantItem>
        fun ofNavigate(): Observable<Screen>
    }

    class ViewModel(
            private val remoteRepository: RemoteRepository,
            private val schedulerProvider: SchedulerProvider

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val title = MutableLiveData<String>().apply { postValue("") }
        val content = MutableLiveData<String>().apply { postValue("") }

        val keyword = MutableLiveData<String>().apply { postValue("") }
        val restaurantItems = MutableLiveData<List<RestaurantItem>>()
        val restaurantItemsVisibility = MutableLiveData<Int>().apply { postValue(View.INVISIBLE) }

        val hashtag = MutableLiveData<String>().apply { postValue("") }

        private val clickNextRelay = PublishRelay.create<Unit>()
        private val navigateRelay = PublishRelay.create<Screen>()

        private val searchRelay = PublishRelay.create<String>()
        private val clickRestaurantItemRelay = BehaviorRelay.create<RestaurantItem>().apply { accept(RestaurantItem("", "", .0, .0)) }

        init {
            disposables.addAll(
                    outputs.ofClickNext()
                            .withLatestFrom(ofScreen<Screen.Write.Note>(), outputs.ofRestaurantClickItem())
                            .map { (_, screen, restaurantItem) ->
                                screen.writeParam.apply {
                                    this.title = this@ViewModel.title.value
                                    this.content = this@ViewModel.content.value
                                    this.restaurant = RestaurantItem.toRestaurant(restaurantItem)
                                }
                            }
                            .subscribeOf(onNext = { inputs.toNavigate(Screen.Write.Photo(it)) }),

                    outputs.ofSearch()
                            .debounce(300, TimeUnit.MILLISECONDS)
                            .doOnNext {
                                if(it.isBlank()) {
                                    restaurantItems.postValue(listOf())
                                    restaurantItemsVisibility.postValue(View.GONE)
                                }
                            }
                            .filter { it.isNotBlank() }
                            .switchMapSingle { remoteRepository.search(it) }
                            .map {
                                it.documents.map { document ->
                                    RestaurantItem(
                                            placeName = document.placeName,
                                            addressName = document.addressName,
                                            x = document.x.toDouble(),
                                            y = document.y.toDouble()
                                    )
                                }
                            }
                            .subscribeOf(onNext = {
                                restaurantItems.postValue(it)
                                restaurantItemsVisibility.postValue(View.VISIBLE)
                            })
            )
        }

        override fun toClickNext() = clickNextRelay.accept(Unit)
        override fun ofClickNext(): Observable<Unit> = clickNextRelay

        override fun toSearch(keyword: String) = searchRelay.accept(keyword)
        override fun ofSearch(): Observable<String> = searchRelay

        override fun toClickRestaurantItem(restaurantItem: RestaurantItem) = clickRestaurantItemRelay.accept(restaurantItem)
        override fun ofRestaurantClickItem(): Observable<RestaurantItem> = clickRestaurantItemRelay

        override fun toNavigate(screen: Screen) = navigateRelay.accept(screen)
        override fun ofNavigate(): Observable<Screen> = navigateRelay
    }
}