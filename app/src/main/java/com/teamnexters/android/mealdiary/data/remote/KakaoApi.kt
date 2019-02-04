package com.teamnexters.android.mealdiary.data.remote

import com.teamnexters.android.mealdiary.data.remote.response.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface KakaoApi {
    @GET("/v2/local/search/keyword.json")
    fun search(
            @Query("query") query: String
    ): Single<SearchResponse>
}