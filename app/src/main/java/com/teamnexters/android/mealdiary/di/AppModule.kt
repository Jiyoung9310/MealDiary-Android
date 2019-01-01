package com.teamnexters.android.mealdiary.di

import android.content.res.Resources
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val appModule = module {
    single { androidContext().resources as Resources }
}