package com.pppp.currencies.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pppp.currencies.data.pokos.Currency
import java.math.BigDecimal

class CurrenciesRecycler @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    RecyclerView(context, attrs, defStyle) {

    var onCurrencyClicked: (String, BigDecimal) -> Unit = { _, _ -> }
        set(value) {
            currenciesAdapter.onCurrencyClicked = value
        }

    private val currenciesAdapter: CurrenciesAdapter
        get() = adapter as CurrenciesAdapter

    init {
        layoutManager = LinearLayoutManager(context)
        adapter = CurrenciesAdapter()
    }

    fun updateRates(currencies: List<Currency>) {
        currenciesAdapter.updateRates(currencies)
    }

}