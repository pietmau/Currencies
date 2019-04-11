package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.Lifecycle
import com.pppp.currencies.data.pokos.Rate

interface RatesViewModel {

    fun subscribe(lifecycle: Lifecycle, callback: (t: List<Rate>) -> Unit)

    fun changeBase(base: String)
}
