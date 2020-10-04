package com.cs.ceren.moviedemo.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val url: String?,
    val title: String,
    val overview: String
) : Parcelable

