package com.teamnexters.android.mealdiary.ui.write.note

import com.teamnexters.android.mealdiary.data.local.entity.Restaurant

internal sealed class RestaurantItem {
    data class FoundedRestaurant(
            val placeName: String,
            val addressName: String,
            val x: Double,
            val y: Double
    ) : RestaurantItem() {
        companion object {
            fun toRestaurant(foundedRestaurantItem: RestaurantItem.FoundedRestaurant): Restaurant {
                return Restaurant(
                        placeName = foundedRestaurantItem.placeName,
                        addressName = foundedRestaurantItem.addressName,
                        x = foundedRestaurantItem.x,
                        y = foundedRestaurantItem.y
                )
            }
        }
    }

    object NotFound : RestaurantItem()
}