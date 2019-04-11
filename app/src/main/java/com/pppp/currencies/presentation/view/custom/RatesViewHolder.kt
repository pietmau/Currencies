package com.pppp.currencies.presentation.view.custom

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pppp.currencies.data.pokos.Rate
import kotlinx.android.synthetic.main.holder_rates.view.*

class RatesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(rate: Rate) {
        itemView.symbol.text = rate.symbol
    }
}