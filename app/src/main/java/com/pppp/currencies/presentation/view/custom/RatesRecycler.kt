package com.pppp.currencies.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pppp.currencies.data.pokos.Rate

class RatesRecycler @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    RecyclerView(context, attrs, defStyle) {

    private val ratesAdapter: RatesAdapter?
        get() = adapter as RatesAdapter

    init {
        layoutManager = LinearLayoutManager(context)
        adapter = RatesAdapter()
    }

    fun setRates(rates: List<Rate>) {
        ratesAdapter?.setRates(rates)
    }
}