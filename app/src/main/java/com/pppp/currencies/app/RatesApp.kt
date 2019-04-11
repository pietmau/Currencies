package com.pppp.currencies.app

import android.app.Application
import com.squareup.leakcanary.LeakCanary


class RatesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}