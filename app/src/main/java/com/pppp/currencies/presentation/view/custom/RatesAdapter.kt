package com.pppp.currencies.presentation.view.custom

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pppp.currencies.R
import com.pppp.currencies.data.pokos.Rate
import com.pppp.currencies.presentation.imageloader.ImageLoader
import com.pppp.currencies.presentation.imageloader.PicassoImageLoader
import com.pppp.currencies.swap

// TODO stableIdes!!!
class RatesAdapter() : RecyclerView.Adapter<RatesViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    private val imageLoader: ImageLoader = PicassoImageLoader()
    private var rates: List<Rate> = listOf()
    lateinit var onSymbolSelected: (String) -> Unit

    init {
        setHasStableIds(true)
    }

    override fun getItemCount() = rates.size

    override fun getItemId(position: Int) = rates[position].symbol.hashCode().toLong()

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
        holder.bind(rates[position], ::onItemClicked)
    }

    fun updateRates(rates: List<Rate>) {
        val newRates = sortRates(rates)
        setRates(newRates)
    }

    private fun setRates(newRates: List<Rate>) {
        Log.e("bar", "received " + newRates.toString())
        Log.e("bar", "old ones " + rates.toString())
        val result = DiffUtil.calculateDiff(RatesDiffUtilCallback(newRates, this.rates))
        this.rates = newRates
        result.dispatchUpdatesTo(this)
    }

    private fun onItemClicked(position: Int) {
        Log.e("bar", "CLICKED --------------------------------------------------------")
        onSymbolSelected(rates[position].symbol)
        val rates = this.rates.swap(position)
        setRates(rates)
        Log.e("bar", "CLICKED --------------------------------------------------------")
        recyclerView.scrollToPosition(0)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    private fun sortRates(rates: List<Rate>): List<Rate> {
        if (this.rates.isEmpty()) {
            return rates
        }
        val mapped = rates.associateBy { it.symbol }
        return this.rates
            .map { rate -> mapped[rate.symbol] }
            .filterNotNull()
    }
}
