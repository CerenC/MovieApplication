package com.cs.ceren.moviedemo.presentation.util

import android.support.v7.util.DiffUtil
import com.cs.ceren.moviedemo.domain.model.Movie

class MovieDiffUtil(
    private val newList: List<Movie>? = null,
    private val oldList: List<Movie>? = null
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition)?.id == newList?.get(newItemPosition)?.id
    }

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition) === newList?.get(newItemPosition)
    }
}