package com.pppp.currencies

import com.pppp.currencies.app.AppComponent
import com.pppp.currencies.app.di.CurrenciesComponent
import com.pppp.currencies.app.di.ProdCurrenciesModule
import com.pppp.currencies.presentation.view.CurrenciesFragment
import com.pppp.currencies.presentation.viewmodel.CurrenciesViewModel
import dagger.Component
import dagger.Module
import dagger.Provides

@Component
abstract class TestAppComponent : AppComponent {
    override fun with(module: ProdCurrenciesModule) = TestCurrenciesComponent()
}

@Module
class TestAppModule(val viewModel: CurrenciesViewModel) {

    @Provides
    fun providesViewModel(): CurrenciesViewModel = viewModel
}

class TestCurrenciesComponent : CurrenciesComponent {
    override fun inject(currenciesFragment: CurrenciesFragment) {

    }
}
