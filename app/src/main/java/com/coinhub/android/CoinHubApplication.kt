package com.coinhub.android

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoinHubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: CoinHubApplication
        fun appContext(): Context = instance.applicationContext
    }
}