package com.teamnexters.android.mealdiary

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen
import com.teamnexters.android.mealdiary.di.appComponent
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree



internal class MealDiaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Fabric.Builder(this)
                .kits(Crashlytics())
                .debuggable(BuildConfig.DEBUG)
                .build()

        FirebaseApp.initializeApp(this)

        AndroidThreeTen.init(this)

        startKoin(this, appComponent)

        if(BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }
}