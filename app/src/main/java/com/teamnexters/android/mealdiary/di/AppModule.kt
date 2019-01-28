package com.teamnexters.android.mealdiary.di

import android.content.res.Resources
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import com.teamnexters.android.mealdiary.ui.*
import com.teamnexters.android.mealdiary.ui.main.DiaryAdapter
import com.teamnexters.android.mealdiary.ui.main.MainViewModel
import com.teamnexters.android.mealdiary.ui.write.WriteViewModel
import com.teamnexters.android.mealdiary.ui.write.photo.GalleryProvider
import com.teamnexters.android.mealdiary.ui.write.photo.GalleryProviderImpl
import com.teamnexters.android.mealdiary.ui.write.photo.PhotoAdapter
import com.teamnexters.android.mealdiary.ui.write.photo.PhotoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { androidContext().resources as Resources }

    single { ToolbarChannelImpl() as ToolbarChannel }

    viewModel { MainViewModel.ViewModel(get(), get()) }

    viewModel { WriteViewModel.ViewModel(get()) }

    viewModel { PhotoViewModel.ViewModel(get(), get()) }

    factory { ToolbarResourcesProviderImpl() as ToolbarResourcesProvider }

    factory { DiaryAdapter() }

    factory { GalleryProviderImpl(get()) as GalleryProvider }

    factory { PhotoAdapter() }
}