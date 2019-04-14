package com.pppp.currencies.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pppp.currencies.R


class CurrenciesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rates_activity)
        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, CurrenciesFragment.newInstance())
            transaction.commit()
        }
    }
}