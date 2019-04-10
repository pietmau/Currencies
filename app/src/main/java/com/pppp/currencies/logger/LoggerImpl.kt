package com.pppp.currencies.logger

import android.util.Log
import com.pppp.currencies.BuildConfig


class LoggerImpl : Logger {

    override fun d(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }
}