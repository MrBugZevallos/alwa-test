package com.example.alwa

import android.app.Application
import com.example.alwa.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AlwaApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AlwaApp)
            modules(appModule)
        }
    }
}