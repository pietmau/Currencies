package com.pppp.currencies.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.pppp.currencies.R
import com.pppp.currencies.app.di.DaggerRatesComponent
import com.pppp.currencies.app.di.RatesModule
import com.pppp.currencies.presentation.viewmodel.CurrenciesViewModel
import kotlinx.android.synthetic.main.rates_fragment.*
import javax.inject.Inject


class CurrenciesFragment : Fragment() {
    @Inject lateinit var viewModel: CurrenciesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ratesModule = RatesModule(requireActivity())
        DaggerRatesComponent.builder().ratesModule(ratesModule).build().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.onCurrencyClicked = { baseSymbol, baseAmount ->
            viewModel.changeBase(baseSymbol, baseAmount)
        }
        recycler.onAmountChanged = { symbol, amount ->

        }
        viewModel.data.observe(requireActivity(), Observer(recycler::updateRates))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.rates_fragment, container, false)

    override fun onPause() {
        super.onPause()
        viewModel.unsubscribe()
    }

    override fun onResume() {
        super.onResume()
        viewModel.subscribe()
    }

    companion object {
        fun newInstance() = CurrenciesFragment()
    }
}