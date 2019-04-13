package com.pppp.currencies.presentation.view.custom

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
class CurrenciesAdapter() : RecyclerView.Adapter<CurrenciesViewHolder>() {
    private lateinit var recyclerView: RecyclerView
    private val imageLoader: ImageLoader = PicassoImageLoader()
    private var currencies: List<Currency> = listOf()
    lateinit var onCurrencyClicked: (String, BigDecimal) -> Unit
    lateinit var onAmountChanged: (String, String) -> Unit

    init {
        setHasStableIds(true)//TODO does it actually do anything?
    }

    override fun getItemCount() = currencies.size

    override fun getItemId(position: Int) = currencies[position].symbol.hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_rates, parent, false)
        return CurrenciesViewHolder(view, imageLoader)
    }

    override fun onBindViewHolder(
        holder: CurrenciesViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }
        holder.bindNewAmount(payloads[0])
    }

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int) {
        holder.bind(currencies[position], ::onItemClicked, ::onAmountChanged, position == 0)
    }

    private fun onAmountChanged(position: Int, amount: String) {
        onAmountChanged(currencies[position].symbol, amount)//TODO position not really neeeded
    }

    fun updateRates(currencies: List<Currency>) {
        val newRates = sortRates(currencies)
        setRates(newRates)
    }

    private fun setRates(newCurrencies: List<Currency>) {
        val result = DiffUtil.calculateDiff(CurrencyDiffCallback(newCurrencies, currencies))
        currencies = newCurrencies
        result.dispatchUpdatesTo(this)
    }

    private fun onItemClicked(position: Int) {
        val newBase = currencies[position]
        onCurrencyClicked(newBase.symbol, newBase.amount)
        val swapped = currencies.swap(position)
        setRates(swapped)
        recyclerView.scrollToPosition(0)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    // TODO comment
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
