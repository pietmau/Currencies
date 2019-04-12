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
    private lateinit var recyclerView: RecyclerView
    private val imageLoader: ImageLoader = PicassoImageLoader()
    private val rates: MutableList<Rate> = mutableListOf()

    override fun getItemCount() = rates.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_rates, parent, false)
        return RatesViewHolder(view, imageLoader)
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        holder.bind(rates[position], ::onItemClicked)
    }

    fun setRates(rates: List<Rate>) {
        val result = DiffUtil.calculateDiff(RatesDiffUtilCallback(rates, this.rates))
        this.rates.clear()
        this.rates.addAll(rates)
        result.dispatchUpdatesTo(this)
    }

    private fun onItemClicked(position: Int) {
        val item = rates.removeAt(position)
        rates.add(0, item)
        notifyItemMoved(position, 0)
        recyclerView.scrollToPosition(0)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }
}