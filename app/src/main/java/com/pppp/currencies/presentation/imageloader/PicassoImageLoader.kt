package com.pppp.currencies.presentation.imageloader

import android.widget.ImageView
import com.squareup.picasso.Picasso

class PicassoImageLoader : ImageLoader {
    override fun loadImage(url: String, view: ImageView) {
        Picasso.get()
            .load(url)
            .into(view);
    }
}