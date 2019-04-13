package com.pppp.currencies.app.di

import com.pppp.currencies.presentation.view.CurrenciesFragment
import dagger.Component

@Component(modules = [CurrenciesModule::class])
interface CurrenciesComponent {
    fun inject(currenciesFragment: CurrenciesFragment)
}