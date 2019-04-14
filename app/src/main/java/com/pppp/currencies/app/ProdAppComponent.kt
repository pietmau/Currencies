package com.pppp.currencies.app

import com.pppp.currencies.app.di.ActivityModule
import com.pppp.currencies.app.di.CurrenciesComponent
import com.pppp.currencies.app.di.ProdCurrenciesComponent
import dagger.Component

@Component(modules = [AppModuleImpl::class])
interface ProdAppComponent : AppComponent {

    override fun with(module: ActivityModule): ProdCurrenciesComponent
}

interface AppComponent {

    fun with(module: ActivityModule): CurrenciesComponent
}
