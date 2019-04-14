package com.pppp.currencies.app.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.pppp.currencies.data.network.client.Client
import com.pppp.currencies.data.network.client.RetrofitClient
import com.pppp.currencies.data.repository.*
import com.pppp.currencies.domain.usecases.GetCurrenciesUseCase
import com.pppp.currencies.domain.usecases.RxGetCurrenciesUseCase
import com.pppp.currencies.presentation.viewmodel.CurrenciesViewModel
import com.pppp.currencies.presentation.viewmodel.RxCurrenciesViewModel
import dagger.Module
import dagger.Provides

@Module
class ProdCurrenciesModule(private val activity: FragmentActivity): CurrenciesModule {

    @Provides
    fun providesViewModel(factory: ViewModelProvider.Factory): CurrenciesViewModel =
        ViewModelProviders.of(activity, factory).get(RxCurrenciesViewModel::class.java)

    @Provides
    internal fun providesUseCase(mapper: Repository): GetCurrenciesUseCase = RxGetCurrenciesUseCase(mapper)

    @Provides
    internal fun provideMapper(client: Client): Repository =
        RepositoryImpl(client, CurrencyCreatorImpl(UrlCreator(), AmountCalculator()))

    @Provides
    internal fun provideClient(): Client = RetrofitClient(cacheDirectory = activity.cacheDir)

    @Provides
    internal fun providefactory(uscase: GetCurrenciesUseCase): ViewModelProvider.Factory =
        RatesViewModelFactory(uscase)
}

class RatesViewModelFactory(private val useCase: GetCurrenciesUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = RxCurrenciesViewModel(useCase) as T
}

interface CurrenciesModule {

}
