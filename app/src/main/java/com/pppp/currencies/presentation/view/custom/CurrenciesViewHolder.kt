package com.pppp.currencies.presentation.view.custom

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.presentation.imageloader.ImageLoader
import kotlinx.android.synthetic.main.holder_rates.view.*

class CurrenciesViewHolder(val view: View, private val imageLoader: ImageLoader) :
    RecyclerView.ViewHolder(view) {

    fun bind(currency: Currency, clickListener: (Int) -> Unit) {
        itemView.symbol.text = currency.symbol
        itemView.country.text = currency.country
        itemView.amount.setText(currency.amount.toPlainString())//TODO use NumberFormat
        imageLoader.loadImage(currency.url, itemView.flag)
        itemView.setOnClickListener {
            clickListener(adapterPosition)
        }
    }

    fun onNewAmount(payload: Any) {
        val currency = (payload as Currency).amount.toPlainString()
        itemView.amount.setText(currency)//TODO use NumberFormat
    }

}