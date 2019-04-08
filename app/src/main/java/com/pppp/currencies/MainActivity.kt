package com.pppp.currencies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pppp.currencies.network.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val client = RetrofitClient(cacheDir = cacheDir)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = client.getRates("EUR")
                foo()
            } catch (exception: Exception) {
                foo()
            }

        }
    }

    private fun foo() {


    }
}
