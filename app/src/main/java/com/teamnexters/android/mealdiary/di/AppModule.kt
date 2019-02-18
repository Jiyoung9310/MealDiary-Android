package com.teamnexters.android.mealdiary.di

import android.content.res.Resources
import com.teamnexters.android.mealdiary.ui.ToolbarChannel
import com.teamnexters.android.mealdiary.ui.ToolbarChannelImpl
import com.teamnexters.android.mealdiary.ui.ToolbarResourcesProvider
import com.teamnexters.android.mealdiary.ui.ToolbarResourcesProviderImpl
import com.teamnexters.android.mealdiary.ui.boarding.BoardingPagerAdapter
import com.teamnexters.android.mealdiary.ui.boarding.BoardingViewModel
import com.teamnexters.android.mealdiary.ui.intro.IntroViewModel
import com.teamnexters.android.mealdiary.ui.main.DiaryAdapter
import com.teamnexters.android.mealdiary.ui.main.MainViewModel
import com.teamnexters.android.mealdiary.ui.splash.SplashViewModel
import com.teamnexters.android.mealdiary.ui.write.WriteViewModel
import com.teamnexters.android.mealdiary.ui.write.note.NoteViewModel
import com.teamnexters.android.mealdiary.ui.write.photo.GalleryProvider
import com.teamnexters.android.mealdiary.ui.write.photo.GalleryProviderImpl
import com.teamnexters.android.mealdiary.ui.write.photo.PhotoAdapter
import com.teamnexters.android.mealdiary.ui.write.photo.PhotoViewModel
import com.teamnexters.android.mealdiary.ui.write.note.RestaurantAdapter
import com.teamnexters.android.mealdiary.ui.write.score.ScoreAdapter
import com.teamnexters.android.mealdiary.ui.write.score.ScoreViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { androidContext().resources as Resources }

    single { ToolbarChannelImpl() as ToolbarChannel }

    viewModel { MainViewModel.ViewModel(get(), get()) }

    viewModel { WriteViewModel.ViewModel(get()) }

    viewModel { NoteViewModel.ViewModel(get(), get()) }

    viewModel { ScoreViewModel.ViewModel(get(), get()) }

    viewModel { PhotoViewModel.ViewModel(get(), get()) }

    viewModel { SplashViewModel.ViewModel(get()) }

    viewModel { IntroViewModel.ViewModel(get()) }

    viewModel { BoardingViewModel.ViewModel(get(), get()) }

    factory { ToolbarResourcesProviderImpl() as ToolbarResourcesProvider }

    factory { DiaryAdapter() }

    factory { GalleryProviderImpl(get()) as GalleryProvider }

    factory { PhotoAdapter() }

    factory { RestaurantAdapter() }

    factory { ScoreAdapter() }

    factory { BoardingPagerAdapter() }
}