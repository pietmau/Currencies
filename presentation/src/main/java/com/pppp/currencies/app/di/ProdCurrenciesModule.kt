package com.pppp.currencies.app.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.pppp.currencies.data.network.client.Client
import com.pppp.currencies.data.network.client.RetrofitClient
import com.pppp.currencies.data.repository.AmountCalculator
import com.pppp.currencies.data.repository.CurrencyCreatorImpl
import com.pppp.currencies.data.repository.RepositoryImpl
import com.pppp.currencies.data.repository.UrlCreator
import com.pppp.currencies.presentation.viewmodel.CurrenciesViewModel
import com.pppp.currencies.presentation.viewmodel.RxCurrenciesViewModel
import com.pppp.currencies.usecases.GetCurrenciesUseCase
import com.pppp.currencies.usecases.RxGetCurrenciesUseCase
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class ProdCurrenciesModule(
) : CurrenciesModule {

    @Provides
    fun providesViewModel(
        factory: ViewModelProvider.Factory,
        activity: AppCompatActivity
    ): CurrenciesViewModel =
        ViewModelProviders.of(activity, factory).get(RxCurrenciesViewModel::class.java)

    @Provides
    fun providesUseCase(mapper: RepositoryImpl): GetCurrenciesUseCase =
        RxGetCurrenciesUseCase(mapper, AndroidSchedulers.mainThread())

    @Provides
    fun provideMapper(client: Client): RepositoryImpl =
        RepositoryImpl(client, CurrencyCreatorImpl(UrlCreator(), AmountCalculator()))

    @Provides
    fun provideClient(activity: AppCompatActivity): Client =
        RetrofitClient(cacheDirectory = activity.cacheDir)

    @Provides
    fun providefactory(uscase: GetCurrenciesUseCase): ViewModelProvider.Factory =
        RatesViewModelFactory(uscase)
}

interface CurrenciesModule {

}

class RatesViewModelFactory(private val useCase: GetCurrenciesUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        RxCurrenciesViewModel(useCase) as T
}
