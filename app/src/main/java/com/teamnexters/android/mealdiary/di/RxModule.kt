package com.teamnexters.android.mealdiary.di

import com.teamnexters.android.mealdiary.util.rx.AppSchedulerProvider
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import org.koin.dsl.module.module

val rxModule = module {
    single { AppSchedulerProvider() as SchedulerProvider }
}