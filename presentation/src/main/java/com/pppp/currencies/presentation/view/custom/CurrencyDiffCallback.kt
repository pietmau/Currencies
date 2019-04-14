package com.pppp.currencies.presentation.view.custom

import androidx.recyclerview.widget.DiffUtil
import com.pppp.currencies.pokos.Currency

//TODO execute on a different thread
class CurrencyDiffCallback(
    private val newCurrencies: List<Currency>,
    private val oldCurrencies: List<Currency>
) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldCurrencies.size

    override fun getNewListSize() = newCurrencies.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        newCurrencies[newItemPosition].symbol.equals(oldCurrencies[oldItemPosition].symbol)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        newCurrencies[newItemPosition].equals(oldCurrencies[oldItemPosition])

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int) =
        newCurrencies[newItemPosition]
}