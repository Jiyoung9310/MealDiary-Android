package com.teamnexters.android.mealdiary.data.remote

import com.teamnexters.android.mealdiary.data.remote.response.SearchResponse
import io.reactivex.Single

internal interface RemoteDataSource {
    fun search(keyword: String): Single<SearchResponse>
}