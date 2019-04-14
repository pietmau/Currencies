package com.pppp.currencies.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView


class FixedRatioImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ImageView(context, attrs, defStyleAttr, defStyleRes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredHeight / 40 * 60
        setMeasuredDimension(width, measuredHeight)
    }
}