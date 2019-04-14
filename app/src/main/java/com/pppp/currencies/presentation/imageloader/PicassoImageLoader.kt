package com.pppp.currencies.presentation.imageloader

import android.widget.ImageView
import com.pppp.currencies.R
import com.squareup.picasso.Picasso

class PicassoImageLoader : ImageLoader {
    override fun loadImage(url: String, view: ImageView) {
        Picasso.get()
            .load(url)
            .error(R.drawable.revoult)
            .placeholder(R.drawable.revoult)
            .into(view);
    }
}