package com.pppp.currencies.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RatesRecycler @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    RecyclerView(context, attrs, defStyle) {

    init {
        layoutManager = LinearLayoutManager(context)
    }
}