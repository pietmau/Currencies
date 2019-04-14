package com.pppp.currencies.usecases

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class Timer() {

    fun run(time: Long): Observable<Long> =
        Observable.timer(time.toLong(), TimeUnit.SECONDS)
}