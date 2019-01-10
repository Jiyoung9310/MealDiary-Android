package com.teamnexters.android.mealdiary.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.teamnexters.android.mealdiary.data.local.entity.Photo
import io.reactivex.Flowable

@Dao
internal abstract class PhotoDao : BaseDao<Photo>() {
    @Query("SELECT * FROM photo")
    abstract fun get(): Flowable<List<Photo>>

    @Query("SELECT * FROM photo WHERE id = :id")
    abstract fun getById(id: Long): Flowable<Photo>

    @Query("DELETE FROM photo WHERE id = :id")
    abstract fun deleteById(id: Long)

    @Query("DELETE FROM photo")
    abstract fun deleteAll()

}