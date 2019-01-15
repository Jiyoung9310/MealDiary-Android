package com.teamnexters.android.mealdiary.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.teamnexters.android.mealdiary.data.local.entity.Restaurant
import io.reactivex.Flowable

@Dao
internal abstract class RestaurantDao : BaseDao<Restaurant>() {
    @Query("SELECT * FROM restaurant")
    abstract fun get(): Flowable<List<Restaurant>>

    @Query("SELECT * FROM restaurant WHERE id = :id")
    abstract fun getById(id: Long): Flowable<Restaurant>

    @Query("DELETE FROM restaurant WHERE id= :id")
    abstract fun deleteById(id: Long)

    @Query("DELETE FROM restaurant")
    abstract fun deleteAll()
}