package com.cs.ceren.moviedemo.presentation.view.adapter

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

class MovieAdapter(
    private val itemClickListener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val items: ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(itemList: List<Movie>) {
        val diffResult = DiffUtil.calculateDiff(MovieDiffUtil(itemList, items))
        diffResult.dispatchUpdatesTo(this)

        items.clear()
        items.addAll(itemList)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            itemView.setOnClickListener { itemClickListener.invoke(movie) }
            movie.url?.let { itemView.movieImage.loadImage(url = movie.url) }
        }
    }

}

