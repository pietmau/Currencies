package com.pppp.currencies.presentation.imageloader

import android.widget.ImageView


interface ImageLoader {

    fun loadImage(url: String, view: ImageView)
}