package org.example.project

import android.app.Application
import com.example.sample.di.initKoin
import dev.icerock.moko.permissions.BuildConfig
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