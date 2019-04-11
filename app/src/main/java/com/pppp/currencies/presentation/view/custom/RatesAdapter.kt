package com.pppp.currencies.presentation.view.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pppp.currencies.R
import com.pppp.currencies.data.pokos.Rate
import com.pppp.currencies.presentation.imageloader.ImageLoader
import com.pppp.currencies.presentation.imageloader.PicassoImageLoader


class RatesAdapter() : RecyclerView.Adapter<RatesViewHolder>() {
    private val imageLoader: ImageLoader = PicassoImageLoader()
    private val rates: MutableList<Rate> = mutableListOf()

    fun setRates(rates: List<Rate>) {
        val result = DiffUtil.calculateDiff(RatesDiffUtilCallback(rates, this.rates))
        this.rates.clear()
        this.rates.addAll(rates)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.holder_rates, parent, false)
        return RatesViewHolder(view, imageLoader)
    }

    override fun getItemCount() = rates.size

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        holder.bind(rates[position])
    }
}