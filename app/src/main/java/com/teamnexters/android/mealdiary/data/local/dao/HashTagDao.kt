package com.teamnexters.android.mealdiary.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.teamnexters.android.mealdiary.data.local.entity.HashTag
import io.reactivex.Flowable

@Dao
internal abstract class HashTagDao : BaseDao<HashTag>() {
    @Query("SELECT * FROM hashtag")
    abstract fun gets(): Flowable<List<HashTag>>

    @Query("SELECT * FROM hashtag WHERE id = :id")
    abstract fun getById(id: Long): Flowable<HashTag>

    @Query("DELETE FROM hashtag WHERE id = :id")
    abstract fun deleteById(id: Long)

    @Query("DELETE FROM hashtag")
    abstract fun deleteAll()
}