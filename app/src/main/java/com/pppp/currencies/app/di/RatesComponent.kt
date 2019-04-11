package com.pppp.currencies.app.di

import com.pppp.currencies.presentation.view.RatesFragment
import dagger.Component

@Component(modules = [RatesModule::class])
interface RatesComponent {
    fun inject(ratesFragment: RatesFragment)
}