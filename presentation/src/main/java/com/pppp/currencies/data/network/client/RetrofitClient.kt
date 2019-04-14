package com.pppp.currencies.data.network.client

import com.pppp.currencies.data.network.api.CurrenciesApi
import com.pppp.currencies.data.network.api.NamesApi
import com.pppp.currencies.data.rates.CurrenciesResponse
import io.reactivex.Observable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RetrofitClient(
    ratesBaseUrl: String = "https://revolut.duckdns.org",
    namesBaseUrl: String = "https://openexchangerates.org",
    cacheDirectory: File,
    TIMEOUT_IN_SECS: Long = 2
) :
    Client {

    private val interceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val cache = Cache(cacheDirectory, 10 * 1024 * 1024)

    private val namesClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(interceptor)
        .build()

    private val ratesClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(TIMEOUT_IN_SECS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_IN_SECS, TimeUnit.SECONDS)
        .build()

    private val ratesApi = Retrofit.Builder()
        .baseUrl(ratesBaseUrl)
        .client(ratesClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(CurrenciesApi::class.java)

    private val namesApi = Retrofit.Builder()
        .baseUrl(namesBaseUrl)
        .client(namesClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(NamesApi::class.java)

    override suspend fun getRatesXXX(base: String): Map<String, Double> = TODO()

    override suspend fun getNamesXXX() = namesApi.getNames()

    override fun getRates(base: String): Observable<CurrenciesResponse> =
        ratesApi.getCurrenciesObservable(base).toObservable()

    override fun getNames() = namesApi.getNamesObservable().toObservable()
}