package com.example.sakeshop

import android.app.Application
import com.example.sakeshop.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SakeShopApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SakeShopApplication)
            modules(appModule)
        }
    }
}