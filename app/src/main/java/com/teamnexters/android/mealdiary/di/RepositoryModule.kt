package com.teamnexters.android.mealdiary.di

import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.repository.LocalRepositoryImpl
import org.koin.dsl.module.module

val localRepositoryModule = module {
    single { LocalRepositoryImpl(get()) as LocalRepository }
}