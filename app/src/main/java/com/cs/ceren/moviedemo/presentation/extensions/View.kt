package com.cs.ceren.moviedemo.presentation.extensions

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cs.ceren.moviedemo.R
import com.cs.ceren.moviedemo.presentation.GlideApp

fun ImageView.loadImage(url: String) {
    GlideApp.with(context.applicationContext)
        .load(url)
        .placeholder(R.drawable.ic_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)

}