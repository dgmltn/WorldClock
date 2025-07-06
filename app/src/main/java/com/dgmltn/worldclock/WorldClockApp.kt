package com.dgmltn.worldclock

import android.app.Application
import com.dgmltn.worldclock.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WorldClockApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WorldClockApp)
            modules(appModule)
        }
    }
}

