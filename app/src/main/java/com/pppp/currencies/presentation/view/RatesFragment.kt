package com.pppp.currencies.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.pppp.currencies.di.DaggerRatesComponent
import com.pppp.currencies.di.RatesModule
import com.pppp.currencies.presentation.viewmodel.RatesViewModel
import javax.inject.Inject


class RatesFragment : Fragment() {
    @Inject lateinit var viewModel: RatesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ratesModule = RatesModule(requireActivity())
        DaggerRatesComponent.builder().ratesModule(ratesModule).build().inject(this)
    }


    companion object {
        fun newInstance() = RatesFragment()
    }
}