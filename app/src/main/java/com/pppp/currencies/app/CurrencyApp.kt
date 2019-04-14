package com.pppp.currencies.app

import android.app.Application
import com.squareup.leakcanary.LeakCanary


class CurrencyApp : Application() {
    var component: ProdAppComponent? = null

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        component = DaggerProdAppComponent.create()
    }
}