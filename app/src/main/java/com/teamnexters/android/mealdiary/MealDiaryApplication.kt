package com.teamnexters.android.mealdiary

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.teamnexters.android.mealdiary.di.appModule
import com.teamnexters.android.mealdiary.di.dbModule
import com.teamnexters.android.mealdiary.di.localRepositoryModule
import com.teamnexters.android.mealdiary.di.rxModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

internal class MealDiaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(
                appModule,
                rxModule,
                dbModule,
                localRepositoryModule
        ))

        Fabric.with(this, Crashlytics())
    }
}