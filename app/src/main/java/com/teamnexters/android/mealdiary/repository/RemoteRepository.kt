package com.teamnexters.android.mealdiary.repository

import com.teamnexters.android.mealdiary.data.remote.response.SearchResponse
import io.reactivex.Single

internal interface RemoteRepository {
    fun search(keyword: String): Single<SearchResponse>
}