package com.pppp.currencies.presentation.view.custom

import androidx.recyclerview.widget.DiffUtil
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.swap

class CurrenciesAdapterDelegate {
    private var currencies: List<Currency> = listOf()
    val size: Int
        get() = currencies.size

    operator fun get(position: Int): Currency = currencies[position]

    fun updateCurrencies(currencies: List<Currency>): DiffUtil.DiffResult {
        if (this.currencies.isEmpty()) {
            return calculateDiffsAndSet(currencies)
        }
        val mapped = currencies.associateBy { it.symbol }
        val zz = this.currencies
            .map { rate -> mapped[rate.symbol] }
            .filterNotNull()
        return calculateDiffsAndSet(zz)
    }

    fun calculateDiffsAndSet(newCurrencies: List<Currency>): DiffUtil.DiffResult {
        val result = DiffUtil.calculateDiff(CurrencyDiffCallback(newCurrencies, currencies))
        currencies = newCurrencies
        return result
    }

    fun swap(position: Int): DiffUtil.DiffResult {
        val swapped = currencies.swap(position)
        val result = calculateDiffsAndSet(swapped)
        return result
    }

    fun isBaseCurrencyPosition(position: Int) = position == 0
}

