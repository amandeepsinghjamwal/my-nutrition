package com.example.mynutrition

import android.app.Application
import com.example.mynutrition.data.DataSource
import com.example.mynutrition.repository.MainRepository
import com.facebook.shimmer.BuildConfig
import timber.log.Timber

class AppClass: Application() {
    fun provideRepo():DataSource{
        return MainRepository()
    }
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}