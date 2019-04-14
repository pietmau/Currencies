package com.pppp.currencies.presentation.view.custom

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pppp.currencies.pokos.Currency
import com.pppp.currencies.presentation.imageloader.ImageLoader
import kotlinx.android.synthetic.main.holder_rates.view.*

class CurrenciesViewHolder(
    val view: View,
    private val imageLoader: ImageLoader,
    private val currencyFormatter: CurrencyFormatter = CurrencyFormatterImpl()
) :
    RecyclerView.ViewHolder(view) {

    fun bind(
        currency: Currency,
        clickListener: (Int) -> Unit,
        amountListener: ((Int, String) -> Unit)?
    ) {
        itemView.symbol.text = currency.symbol
        itemView.country.text = currency.name
        itemView.amount_input.setText(currencyFormatter.format(currency.amount))
        imageLoader.loadImage(currency.url, itemView.flag)
        itemView.setOnClickListener {
            clickListener(adapterPosition)
            itemView.amount_input.requestFocus()
        }
        itemView.amount_input.addTextChangedListener(SimpleTextWatcher({
            amountListener?.invoke(adapterPosition, it)
        }))
    }

    fun bindNewAmount(payload: Any) {
        val currency = currencyFormatter.format((payload as Currency).amount)
        itemView.amount_input.setText(currency)//TODO use NumberFormat
    }

}