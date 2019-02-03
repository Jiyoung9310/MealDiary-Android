package com.teamnexters.android.mealdiary.ui.write.restaurant

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Restaurant
import com.teamnexters.android.mealdiary.repository.RemoteRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit

internal interface RestaurantViewModel {
    interface Inputs {
        fun toKeyword(keyword: String)
        fun toClickRestauntItem(restaurantItem: RestaurantItem)
        fun toNavigate(screen: Screen)
    }

    interface Outputs {
        fun ofKeyword(): Observable<String>
        fun ofRestaurantClickItem(): Observable<RestaurantItem>
        fun ofNavigate(): Observable<Screen>
    }

    class ViewModel(
            private val remoteRepository: RemoteRepository

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val keyword = MutableLiveData<String>()
        val restaurantItems = MutableLiveData<List<RestaurantItem>>()

        private val keywordRelay = BehaviorRelay.createDefault("")

        private val navigateRelay = PublishRelay.create<Screen>()
        private val clickRestaurantItemRelay = PublishRelay.create<RestaurantItem>()

        init {
            disposables.addAll(
                    outputs.ofKeyword()
                            .doOnNext { keyword.postValue(it) }
                            .debounce(500, TimeUnit.MILLISECONDS)
                            .filter {
                                if(it.isBlank()) {
                                    restaurantItems.postValue(listOf())
                                }

                                it.isNotBlank()
                            }
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
                            .subscribeOf(onNext = { restaurantItems.postValue(it) }),

                    outputs.ofRestaurantClickItem()
                            .throttleClick()
                            .map { RestaurantItem.toRestaurant(it) }
                            .subscribeOf(onNext = { })
            )
        }

        override fun toKeyword(keyword: String) = keywordRelay.accept(keyword)
        override fun ofKeyword(): Observable<String> = keywordRelay

        override fun toClickRestauntItem(restaurantItem: RestaurantItem) = clickRestaurantItemRelay.accept(restaurantItem)
        override fun ofRestaurantClickItem(): Observable<RestaurantItem> = clickRestaurantItemRelay

        override fun toNavigate(screen: Screen) = navigateRelay.accept(screen)
        override fun ofNavigate(): Observable<Screen> = navigateRelay
    }
}