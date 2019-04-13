package com.pppp.currencies.app.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.pppp.currencies.data.mapper.CurrencyCreatorImpl
import com.pppp.currencies.data.mapper.RxMapper
import com.pppp.currencies.data.mapper.RxMapperImpl
import com.pppp.currencies.data.mapper.UrlCreator
import com.pppp.currencies.data.network.client.Client
import com.pppp.currencies.data.network.client.RetrofitClient
import com.pppp.currencies.domain.usecases.GetRatesUseCase
import com.pppp.currencies.domain.usecases.RxGetRatesUseCase
import com.pppp.currencies.presentation.viewmodel.CurrenciesViewModel
import com.pppp.currencies.presentation.viewmodel.RxCurrenciesViewModel
import dagger.Module
import dagger.Provides

@Module
class RatesModule(private val activity: FragmentActivity) {

    @Provides
    fun providesViewModel(factory: ViewModelProvider.Factory): CurrenciesViewModel =
        ViewModelProviders.of(activity, factory).get(RxCurrenciesViewModel::class.java)

    @Provides
    internal fun providesUseCase(mapper: RxMapper): GetRatesUseCase = RxGetRatesUseCase(mapper)

    @Provides
    internal fun provideMapper(client: Client): RxMapper =
        RxMapperImpl(client, CurrencyCreatorImpl(UrlCreator()))

    @Provides
    internal fun provideClient(): Client = RetrofitClient(cacheDirectory = activity.cacheDir)

    @Provides
    internal fun providefactory(uscase: GetRatesUseCase): ViewModelProvider.Factory =
        RatesViewModelFactory(uscase)
}

class RatesViewModelFactory(private val useCase: GetRatesUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = RxCurrenciesViewModel(useCase) as T
}