package com.pppp.currencies.presentation.view.custom

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.pppp.currencies.data.pokos.Currency

//TODO execute on a different thread
class CurrenciesDiffUtilCallback(
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


    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        Log.e("foo", "new " + newCurrencies[newItemPosition].toString())
        Log.e("foo", "old " + oldCurrencies[oldItemPosition].toString())
        return newCurrencies[newItemPosition]
    }
}