package com.pppp.currencies.presentation.view.custom

import androidx.recyclerview.widget.DiffUtil
import com.pppp.currencies.data.pokos.Rate

//TODO execute on a different thread
class RatesDiffUtilCallback(private val newRates: List<Rate>, private val oldRates: List<Rate>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        newRates[newItemPosition].symbol.equals(oldRates[oldItemPosition].symbol)

    override fun getOldListSize() = oldRates.size

    override fun getNewListSize() = newRates.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        newRates[newItemPosition].equals(oldRates[oldItemPosition])
}