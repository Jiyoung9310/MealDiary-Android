package com.teamnexters.android.mealdiary.ui.write.restaurant

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.repository.RemoteRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import com.teamnexters.android.mealdiary.util.extension.withLatestFromSecond
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import java.util.concurrent.TimeUnit

internal interface RestaurantViewModel {
    interface Inputs {
        fun toKeyword(keyword: String)
        fun toClickRestaurantItem(restaurantItem: RestaurantItem)
        fun toNavigate(screen: Screen)
        fun toClickSkip()
    }

    interface Outputs {
        fun ofKeyword(): Observable<String>
        fun ofRestaurantClickItem(): Observable<RestaurantItem>
        fun ofNavigate(): Observable<Screen>
        fun ofClickSkip(): Observable<Unit>
    }

    class ViewModel(
            private val remoteRepository: RemoteRepository

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val keyword = MutableLiveData<String>()
        val restaurantItems = MutableLiveData<List<RestaurantItem>>()
        val listVisibility = MutableLiveData<Int>().apply { postValue(View.INVISIBLE) }

        private val keywordRelay = BehaviorRelay.createDefault("")

        private val navigateRelay = PublishRelay.create<Screen>()
        private val clickRestaurantItemRelay = PublishRelay.create<RestaurantItem>()
        private val clickSkipRelay = PublishRelay.create<Unit>()

        init {
            disposables.addAll(
                    outputs.ofKeyword()
                            .doOnNext { keyword.postValue(it) }
                            .debounce(500, TimeUnit.MILLISECONDS)
                            .doOnNext {
                                if(it.isBlank()) {
                                    restaurantItems.postValue(listOf())
                                    listVisibility.postValue(View.GONE)
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
                                listVisibility.postValue(View.VISIBLE)
                            }),

                    outputs.ofRestaurantClickItem()
                            .throttleClick()
                            .withLatestFrom(ofScreen<Screen.Write.Restaurant>())
                            .map {
                                it.second.writeParam.apply {
                                    restaurant = RestaurantItem.toRestaurant(it.first)
                                }
                            }
                            .subscribeOf(onNext = { inputs.toNavigate(Screen.Write.Note(it)) }),

                    outputs.ofClickSkip()
                            .throttleClick()
                            .withLatestFromSecond(ofScreen<Screen.Write.Restaurant>())
                            .subscribeOf(onNext = { inputs.toNavigate(Screen.Write.Note(it.writeParam)) })
            )
        }

        override fun toKeyword(keyword: String) = keywordRelay.accept(keyword)
        override fun ofKeyword(): Observable<String> = keywordRelay

        override fun toClickRestaurantItem(restaurantItem: RestaurantItem) = clickRestaurantItemRelay.accept(restaurantItem)
        override fun ofRestaurantClickItem(): Observable<RestaurantItem> = clickRestaurantItemRelay

        override fun toNavigate(screen: Screen) = navigateRelay.accept(screen)
        override fun ofNavigate(): Observable<Screen> = navigateRelay

        override fun toClickSkip() = clickSkipRelay.accept(Unit)
        override fun ofClickSkip(): Observable<Unit> = clickSkipRelay
    }
}