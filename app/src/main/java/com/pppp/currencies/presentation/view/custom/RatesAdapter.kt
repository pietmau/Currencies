package com.pppp.currencies.presentation.view.custom

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pppp.currencies.R
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.presentation.imageloader.ImageLoader
import com.pppp.currencies.presentation.imageloader.PicassoImageLoader
import com.pppp.currencies.swap
import java.math.BigDecimal

// TODO stableIdes!!!
class RatesAdapter() : RecyclerView.Adapter<RatesViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    private val imageLoader: ImageLoader = PicassoImageLoader()
    private var currencies: List<Currency> = listOf()
    lateinit var onCurrencyClicked: (String, BigDecimal) -> Unit

    init {
        setHasStableIds(true)
    }

    override fun getItemCount() = currencies.size

    override fun getItemId(position: Int) = currencies[position].symbol.hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_rates, parent, false)
        return RatesViewHolder(view, imageLoader)
    }

    override fun onBindViewHolder(holder: RatesViewHolder, pos: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, pos)
            return
        }
        payloads
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        holder.bind(currencies[position], ::onItemClicked)
    }

    fun updateRates(currencies: List<Currency>) {
        val newRates = sortRates(currencies)
        setRates(newRates)
    }

    private fun setRates(newCurrencies: List<Currency>) {
        Log.e("bar", "received " + newCurrencies.toString())
        Log.e("bar", "old ones " + currencies.toString())
        val result = DiffUtil.calculateDiff(RatesDiffUtilCallback(newCurrencies, this.currencies))
        this.currencies = newCurrencies
        result.dispatchUpdatesTo(this)
    }

    private fun onItemClicked(position: Int) {
        Log.e("bar", "CLICKED --------------------------------------------------------")
        val newBase = currencies[position]
        onCurrencyClicked(newBase.symbol, newBase.amount)
        val swapped = this.currencies.swap(position)
        setRates(swapped)
        Log.e("bar", "CLICKED --------------------------------------------------------")
        recyclerView.scrollToPosition(0)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    private fun sortRates(currencies: List<Currency>): List<Currency> {
        if (this.currencies.isEmpty()) {
            return currencies
        }
        val mapped = currencies.associateBy { it.symbol }
        return this.currencies
            .map { rate -> mapped[rate.symbol] }
            .filterNotNull()
    }
}
