package com.pppp.currencies.presentation.view.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pppp.currencies.R
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.presentation.imageloader.ImageLoader
import com.pppp.currencies.presentation.imageloader.PicassoImageLoader
import java.math.BigDecimal

class CurrenciesAdapter(
    private val delegate: CurrenciesAdapterDelegate = CurrenciesAdapterDelegate(),
    private val imageLoader: ImageLoader = PicassoImageLoader()
) :
    RecyclerView.Adapter<CurrenciesViewHolder>() {
    private lateinit var recyclerView: RecyclerView
    lateinit var onCurrencyClicked: (String, BigDecimal) -> Unit
    lateinit var onAmountChanged: (String, String) -> Unit

    override fun getItemCount() = delegate.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_rates, parent, false)
        return CurrenciesViewHolder(view, imageLoader)
    }

    override fun onBindViewHolder(
        holder: CurrenciesViewHolder, position: Int, payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }
        if (position == 0) {
            return
        }
        holder.bindNewAmount(payloads[0])
    }

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int) {
        holder.bind(delegate[position], ::onItemClicked, ::onAmountChanged)
    }

    fun updateCurrencies(currencies: List<Currency>) {
        val result = delegate.updateCurrencies(currencies)
        result.dispatchUpdatesTo(this)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    private fun onItemClicked(position: Int) {
        val newBase = delegate[position]
        onCurrencyClicked(newBase.symbol, newBase.amount)
        val result = delegate.swap(position)
        result.dispatchUpdatesTo(this)
        recyclerView.scrollToPosition(0)
    }

    private fun onAmountChanged(position: Int, amount: String) {
        if (delegate.isBaseCurrencyPosition(position)) {
            onAmountChanged(delegate[position].symbol, amount)
        }
    }
}
