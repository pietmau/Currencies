package com.pppp.currencies.presentation.view.custom

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pppp.currencies.data.pokos.Rate
import com.pppp.currencies.presentation.imageloader.ImageLoader
import kotlinx.android.synthetic.main.holder_rates.view.*

class RatesViewHolder(val view: View, private val imageLoader: ImageLoader) :
    RecyclerView.ViewHolder(view) {

    fun bind(rate: Rate) {
        itemView.symbol.text = rate.symbol
        itemView.country.text = rate.country
        imageLoader.loadImage(rate.url, itemView.flag)
    }
}