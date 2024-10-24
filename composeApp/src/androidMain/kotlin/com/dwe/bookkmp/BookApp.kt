package com.dwe.bookkmp

import android.app.Application
import com.dwe.bookkmp.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class BookApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(this@BookApp)
        }
    }
}