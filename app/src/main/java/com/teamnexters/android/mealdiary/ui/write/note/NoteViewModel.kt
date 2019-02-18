package com.teamnexters.android.mealdiary.ui.write.note

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Restaurant
import com.teamnexters.android.mealdiary.repository.RemoteRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.HashTagUtil
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
        fun toSelectedRestaurant(restaurant: Restaurant)
        fun toNavigate(screen: Screen)
    }

    interface Outputs {
        fun ofClickNext(): Observable<Unit>
        fun ofSearch(): Observable<String>
        fun ofRestaurantClickItem(): Observable<RestaurantItem>
        fun ofSelectedRestaurant(): Observable<Restaurant>
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

        val keyword = MutableLiveData<CharSequence>().apply { postValue("") }
        val keywordTextColor = MutableLiveData<Int>().apply { postValue(R.color.black) }

        val restaurantItems = MutableLiveData<List<RestaurantItem>>()
        val restaurantItemsVisibility = MutableLiveData<Int>().apply { postValue(View.INVISIBLE) }

        val hashTag = MutableLiveData<String>().apply { postValue("") }

        private val clickNextRelay = PublishRelay.create<Unit>()
        private val navigateRelay = PublishRelay.create<Screen>()

        private val searchRelay = PublishRelay.create<String>()
        private val clickRestaurantItemRelay = PublishRelay.create<RestaurantItem>()
        private val selectedRestaurantRelay = BehaviorRelay.createDefault(Restaurant("", ""))

        init {
            disposables.addAll(
                    outputs.ofClickNext()
                            .withLatestFrom(ofScreen<Screen.Write.Note>(), outputs.ofSelectedRestaurant())
                            .map { (_, screen, selectedRestaurant) ->
                                screen.writeParam.apply {
                                    this.title = this@ViewModel.title.value
                                    this.content = this@ViewModel.content.value
                                    this.restaurant = selectedRestaurant

                                    hashTag.value?.let { this.hashTags = HashTagUtil.toHashTagList(it) }
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
                                    RestaurantItem.FoundedRestaurant(
                                            placeName = document.placeName,
                                            addressName = document.addressName,
                                            x = document.x.toDouble(),
                                            y = document.y.toDouble()
                                    )
                                }.toMutableList<RestaurantItem>().apply {
                                    add(RestaurantItem.NotFound)
                                }
                            }
                            .subscribeOf(onNext = {
                                restaurantItems.postValue(it)
                                restaurantItemsVisibility.postValue(View.VISIBLE)
                            }, onError = { }),

                    outputs.ofRestaurantClickItem()
                            .subscribeOf(onNext = { restaurantItem ->
                                val selectedRestaurant = when(restaurantItem) {
                                    is RestaurantItem.FoundedRestaurant -> {
                                        RestaurantItem.FoundedRestaurant.toRestaurant(restaurantItem)
                                    }
                                    is RestaurantItem.NotFound -> {
                                        Restaurant(keyword.value.toString(), keyword.value.toString())
                                    }
                                }

                                inputs.toSelectedRestaurant(selectedRestaurant)

                                keyword.postValue(selectedRestaurant.placeName)

                                keywordTextColor.postValue(R.color.primary_orange)
                            }),

                    outputs.ofSearch()
                            .withLatestFrom(outputs.ofSelectedRestaurant())
                            .filter { it.first != it.second.placeName }
                            .subscribeOf(onNext = {
                                inputs.toSelectedRestaurant(Restaurant("", ""))

                                keywordTextColor.postValue(R.color.black)
                            })
            )
        }

        override fun toClickNext() = clickNextRelay.accept(Unit)
        override fun ofClickNext(): Observable<Unit> = clickNextRelay

        override fun toSearch(keyword: String) = searchRelay.accept(keyword)
        override fun ofSearch(): Observable<String> = searchRelay

        override fun toClickRestaurantItem(restaurantItem: RestaurantItem) = clickRestaurantItemRelay.accept(restaurantItem)
        override fun ofRestaurantClickItem(): Observable<RestaurantItem> = clickRestaurantItemRelay

        override fun toSelectedRestaurant(restaurant: Restaurant) = selectedRestaurantRelay.accept(restaurant)
        override fun ofSelectedRestaurant(): Observable<Restaurant> = selectedRestaurantRelay

        override fun toNavigate(screen: Screen) = navigateRelay.accept(screen)
        override fun ofNavigate(): Observable<Screen> = navigateRelay
    }
}