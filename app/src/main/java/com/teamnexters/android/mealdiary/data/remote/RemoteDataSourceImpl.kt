package com.teamnexters.android.mealdiary.data.remote

import com.teamnexters.android.mealdiary.data.remote.response.SearchResponse
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Single

internal class RemoteDataSourceImpl(
        private val kakaoApi: KakaoApi,
        private val schedulerProvider: SchedulerProvider

) : RemoteDataSource {

    override fun search(keyword: String): Single<SearchResponse> {
        return kakaoApi.search(keyword).subscribeOn(schedulerProvider.io())
    }

}