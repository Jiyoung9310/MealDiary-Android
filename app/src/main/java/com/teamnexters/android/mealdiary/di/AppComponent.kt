package com.teamnexters.android.mealdiary.di

val appComponent = listOf(
        appModule,
        rxModule,
        dbModule,
        networkModule,
        localRepositoryModule,
        remoteRepositoryModule
)
