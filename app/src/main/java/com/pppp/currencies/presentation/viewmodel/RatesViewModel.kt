package com.pppp.currencies.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.pppp.currencies.data.pokos.Rate

interface RatesViewModel {


    fun changeBase(base: String)
    val data: MutableLiveData<List<Rate>>
    fun subscribe()
    fun unsubscribe()
}
