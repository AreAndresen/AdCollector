package com.andresen.adcollector

import android.app.Application
import com.andresen.adcollector.main.koinmodules.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class AdCollectorApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AdCollectorApplication)
            fragmentFactory()
            modules(KoinModules.module(this@AdCollectorApplication))
        }

    }
}
