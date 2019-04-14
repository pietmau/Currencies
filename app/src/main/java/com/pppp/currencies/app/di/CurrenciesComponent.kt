package com.pppp.currencies.app.di

import com.pppp.currencies.presentation.view.CurrenciesFragment
import dagger.Subcomponent

/**
 * Only so that we can swap dependencies during Espresso tests.
 */
@Subcomponent(modules = [ProdCurrenciesModule::class])
interface CurrenciesComponent {
    fun inject(currenciesFragment: CurrenciesFragment)
}