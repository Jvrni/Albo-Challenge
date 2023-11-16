package com.jvrni.albo_challenge

import android.app.Application
import com.jvrni.albo_challenge.di.serviceModule
import com.jvrni.albo_challenge.di.uiModule
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Load modules
            modules(
                uiModule,
                serviceModule
            )
        }
    }
}