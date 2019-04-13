package com.pppp.currencies

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pppp.currencies.data.network.client.RetrofitClient
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val client = RetrofitClient(cacheDirectory = cacheDir)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                //val result = client.getRates("EUR")
                foo()
            } catch (exception: Exception) {
                foo()
            }
        }

        val time = Flowable.interval(1, TimeUnit.SECONDS)
        val delay = Observable.just(1).delay(3, TimeUnit.SECONDS)
        time
            .onBackpressureDrop()
            .doOnNext { Log.e("foo", "producing " + it) }
            .subscribeOn(Schedulers.io())
            .flatMap({
                Thread.sleep(3 * 1000)
                Flowable.just(it)
            }, 1)
            .observeOn(AndroidSchedulers.mainThread())
        //.subscribe { Log.e("foo", "reciveed " + it) }

//        val z =
//            RxGetRatesUseCase(RxMapperImpl(RetrofitClient(cacheDir = this.cacheDir))).subscribe(
//                Observable.interval(3, TimeUnit.SECONDS).zipWith(Observable.just("EUR", "GBP"),
//                    BiFunction { time: Long, symbol: String -> symbol }),
//                {
//                    Log.e("foo", it.toString())
//                },
//                {
//
//                })

//        val rxRatesViewModel =
//            RxCurrenciesViewModel(RxGetRatesUseCase(RxMapperImpl(RetrofitClient(cacheDirectory = cacheDir), UrlCreator())))
//        rxRatesViewModel
//            .subscribe(this.lifecycle, {
//                Log.e("foo", it.toString())
//            })
//        rxRatesViewModel.changeBase("EUR")
//        Handler().postDelayed({
//            rxRatesViewModel.changeBase("GBP")
//            rxRatesViewModel.unsubscribe()
//        }, 3 * 1000)
//        Handler().postDelayed({
//            rxRatesViewModel.subscribe()
//        }, 3 * 1000)
    }

    private fun rand(): Long {
        val nextLong = Random.nextLong(5 * 1000)
        Log.d("foo", "sleeping " + nextLong)
        return nextLong
    }

    private fun foo() {


    }
}
