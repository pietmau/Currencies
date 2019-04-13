package com.pppp.currencies.presentation.view.custom

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.pppp.currencies.data.pokos.Currency

//TODO execute on a different thread
class RatesDiffUtilCallback(private val newCurrencies: List<Currency>, private val oldCurrencies: List<Currency>) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldCurrencies.size

    override fun getNewListSize() = newCurrencies.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val equals = newCurrencies[newItemPosition].symbol.equals(oldCurrencies[oldItemPosition].symbol)
        if (!equals) {
            Log.e("foo", "NOT EQUAL")
            Log.e("foo", oldCurrencies[oldItemPosition].toString())
            Log.e("foo", newCurrencies[newItemPosition].toString())
        }
        return equals
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val equals = newCurrencies[newItemPosition].equals(oldCurrencies[oldItemPosition])
        if (!equals) {
            Log.e("foo", "DIFFERENCE!")
            Log.e("foo", oldCurrencies[oldItemPosition].toString())
            Log.e("foo", newCurrencies[newItemPosition].toString())
        }
        return equals
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return 0
    }
}