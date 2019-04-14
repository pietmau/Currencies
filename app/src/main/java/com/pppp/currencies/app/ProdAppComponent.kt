package com.pppp.currencies.app

import com.pppp.currencies.app.di.CurrenciesComponent
import com.pppp.currencies.app.di.ProdCurrenciesModule
import dagger.Component

@Component(modules = [AppModuleImpl::class])
interface ProdAppComponent : AppComponent {

    override fun with(module: ProdCurrenciesModule): CurrenciesComponent
}

interface AppComponent {

    fun with(module: ProdCurrenciesModule): CurrenciesComponent
}
