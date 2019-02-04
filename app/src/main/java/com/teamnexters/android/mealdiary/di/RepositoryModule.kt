package com.teamnexters.android.mealdiary.di

import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.repository.LocalRepositoryImpl
import com.teamnexters.android.mealdiary.repository.RemoteRepository
import com.teamnexters.android.mealdiary.repository.RemoteRepositoryImpl
import org.koin.dsl.module.module

val localRepositoryModule = module {
    single { LocalRepositoryImpl(get()) as LocalRepository }
}

val remoteRepositoryModule = module {
    single { RemoteRepositoryImpl(get()) as RemoteRepository }
}