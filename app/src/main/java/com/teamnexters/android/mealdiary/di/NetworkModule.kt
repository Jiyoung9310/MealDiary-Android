package com.teamnexters.android.mealdiary.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.teamnexters.android.mealdiary.BuildConfig
import com.teamnexters.android.mealdiary.data.remote.KakaoApi
import com.teamnexters.android.mealdiary.data.remote.RemoteDataSource
import com.teamnexters.android.mealdiary.data.remote.RemoteDataSourceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor {

                    val requestBuilder = it.request().newBuilder()
                            .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_API_KEY}")


                    it.proceed(requestBuilder.build())
                }
                .addNetworkInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                        }
                )

        if(BuildConfig.DEBUG) {
            okHttpClientBuilder.addNetworkInterceptor(StethoInterceptor())
        }

        val okHttpClient = okHttpClientBuilder.build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com/")
                .client(okHttpClient)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                GsonBuilder().setPrettyPrinting().create()
                        )
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()


        retrofit.create(KakaoApi::class.java)
    }

    single { RemoteDataSourceImpl(get(), get()) as RemoteDataSource }
}

