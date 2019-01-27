package com.cs.ceren.moviedemo.presentation.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cs.ceren.moviedemo.presentation.GlideApp

fun ImageView.loadImage(url: String, context: Context) {
    //TODO add listener to handle error
    GlideApp.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)

}