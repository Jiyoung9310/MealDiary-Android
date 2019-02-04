package com.teamnexters.android.mealdiary.repository

import com.teamnexters.android.mealdiary.data.remote.RemoteDataSource
import com.teamnexters.android.mealdiary.data.remote.response.SearchResponse
import io.reactivex.Single

internal class RemoteRepositoryImpl(
    private val remoteDataSource: RemoteDataSource

) : RemoteRepository {

    override fun search(keyword: String): Single<SearchResponse> {
        return remoteDataSource.search(keyword)
    }

}