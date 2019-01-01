package com.teamnexters.android.mealdiary.di

import android.content.res.Resources
import com.teamnexters.android.mealdiary.ui.main.DiaryAdapter
import com.teamnexters.android.mealdiary.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val appModule = module {
    single { androidContext().resources as Resources }

    single { MainViewModel(get()) }

    factory { DiaryAdapter() }
}