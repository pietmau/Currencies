package com.pppp.currencies

import com.pppp.currencies.app.AppComponent
import com.pppp.currencies.app.di.CurrenciesComponent
import com.pppp.currencies.app.di.CurrenciesModule
import dagger.Component
import dagger.Subcomponent

@Component()
interface TestAppComponent : AppComponent {

    override fun with(module: CurrenciesModule): TestCurrenciesComponent
}

@Subcomponent(modules = [TestCurrenciesModule::class])
interface TestCurrenciesComponent : CurrenciesComponent

class TestCurrenciesModule {

}
