package com.teamnexters.android.mealdiary

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.teamnexters.android.mealdiary.di.appComponent
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

internal class MealDiaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, appComponent)

        Fabric.with(this, Crashlytics())
    }
}