package com.pppp.currencies.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pppp.currencies.R
import com.pppp.currencies.presentation.view.RatesFragment


class RatesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rates_activity)
        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, RatesFragment.newInstance())
            transaction.commit()
        }
    }
}