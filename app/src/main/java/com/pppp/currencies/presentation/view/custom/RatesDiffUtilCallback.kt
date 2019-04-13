package com.pppp.currencies.presentation.view.custom

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.pppp.currencies.data.pokos.Rate

//TODO execute on a different thread
class RatesDiffUtilCallback(private val newRates: List<Rate>, private val oldRates: List<Rate>) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldRates.size

    override fun getNewListSize() = newRates.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val equals = newRates[newItemPosition].symbol.equals(oldRates[oldItemPosition].symbol)
        if (!equals) {
            Log.e("foo", "NOT EQUAL")
            Log.e("foo", oldRates[oldItemPosition].toString())
            Log.e("foo", newRates[newItemPosition].toString())
        }
        return equals
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val equals = newRates[newItemPosition].equals(oldRates[oldItemPosition])
        if (!equals) {
            Log.e("foo", "DIFFERENCE!")
            Log.e("foo", oldRates[oldItemPosition].toString())
            Log.e("foo", newRates[newItemPosition].toString())
        }
        return equals
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return 0
    }
}