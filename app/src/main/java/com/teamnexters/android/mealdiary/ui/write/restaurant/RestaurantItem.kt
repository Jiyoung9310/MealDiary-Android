package com.teamnexters.android.mealdiary.ui.write.restaurant

import com.teamnexters.android.mealdiary.data.local.entity.Restaurant

internal data class RestaurantItem(
        val placeName: String,
        val addressName: String,
        val x: Double,
        val y: Double
) {
    companion object {
        fun toRestaurant(restaurantItem: RestaurantItem): Restaurant {
            return Restaurant(
                    placeName = restaurantItem.placeName,
                    addressName = restaurantItem.addressName,
                    x = restaurantItem.x,
                    y = restaurantItem.y
            )
        }
    }
}