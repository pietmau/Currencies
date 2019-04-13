package com.pppp.currencies.app.di

import com.pppp.currencies.presentation.view.CurrenciesFragment
import dagger.Component

@Component(modules = [RatesModule::class])
interface RatesComponent {
    fun inject(currenciesFragment: CurrenciesFragment)
}