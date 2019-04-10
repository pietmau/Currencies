package com.pppp.currencies.data.network.client

import com.pppp.currencies.data.network.api.NamesApi
import com.pppp.currencies.data.network.api.RatesApi
import io.reactivex.Observable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class RetrofitClient(
    ratesBaseUrl: String = "https://revolut.duckdns.org",
    namesBaseUrl: String = "https://openexchangerates.org",
    cacheDirectory: File
) :
    Client {

    private val interceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val cache = Cache(cacheDirectory, 10 * 1024 * 1024)
    private val client = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(interceptor)
        .build()

    private val ratesApi = Retrofit.Builder()
        .baseUrl(ratesBaseUrl)
        .client(client)
        //.addConverterFactory(RatesConverter())TODO
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RatesApi::class.java)

    private val namesApi = Retrofit.Builder()
        .baseUrl(namesBaseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(NamesApi::class.java)

    override suspend fun getRates(base: String): Map<String, Double> =
        ratesApi.getRates(base).rates ?: emptyMap()

    override suspend fun getNames() = namesApi.getNames()

    override fun getRatesObservable(base: String): Observable<Map<String, Double>> =
        ratesApi.getRatesObservable(base).toObservable().map { it.rates ?: emptyMap() }

    override fun getNamesObservable() = namesApi.getNamesObservable().toObservable()
}