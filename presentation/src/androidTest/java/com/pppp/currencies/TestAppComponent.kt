package com.pppp.currencies

import com.pppp.currencies.app.AppComponent
import com.pppp.currencies.app.di.ActivityModule
import com.pppp.currencies.app.di.CurrenciesComponent
import com.pppp.currencies.presentation.viewmodel.CurrenciesViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Component(modules = [TestAppModule::class])
abstract class TestAppComponent : AppComponent {
    abstract override fun with(module: ActivityModule): TestCurrenciesComponent
}

// We provide the test dependency here
@Module
class TestAppModule(val viewModel: CurrenciesViewModel) {

    @Provides
    fun providesViewModel(): CurrenciesViewModel = viewModel
}

// It does nothing, so that we can use the TestAppModule to inject what we want
@Subcomponent(modules = [ActivityModule::class])
abstract class TestCurrenciesComponent : CurrenciesComponent
