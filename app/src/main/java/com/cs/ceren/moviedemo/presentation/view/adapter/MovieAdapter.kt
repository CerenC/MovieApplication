package com.cs.ceren.moviedemo.presentation.view.adapter

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cs.ceren.moviedemo.R
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.presentation.extensions.loadImage
import com.cs.ceren.moviedemo.presentation.util.MovieDiffUtil
import kotlinx.android.synthetic.main.list_item_movie.view.*
import java.util.*

class MovieAdapter(context: Context) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    internal var listener: ItemTouchListener

    init {
        listener = context as ItemTouchListener
    }

    private val list: ArrayList<Movie> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(view, context = parent.context, listener = listener)
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setItems(itemList: List<Movie>) {
        val diffResult = DiffUtil.calculateDiff(MovieDiffUtil(itemList, list))
        diffResult.dispatchUpdatesTo(this)
        list.clear()
        list.addAll(itemList)
    }

    inner class MovieViewHolder(itemView: View, context: Context, listener: ItemTouchListener) :
        RecyclerView.ViewHolder(itemView) {
        private val context = context
        fun bind(movie: Movie) {
            itemView.setOnClickListener { listener.onItemClick(movie) }
            movie.url?.let { itemView.movieImage.loadImage(url = movie.url, context = context) }
        }

    }

    interface ItemTouchListener {
        fun onItemClick(item: Movie)
    }


}

