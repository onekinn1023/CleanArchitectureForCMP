package org.example.project

import android.app.Application
import android.os.Build
import dev.icerock.moko.permissions.BuildConfig
import di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
           config = { androidContext(this@MyApplication)},
            logEnabled = BuildConfig.DEBUG
        )
    }
}