package com.cs.ceren.moviedemo.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cs.ceren.moviedemo.R
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.presentation.extensions.loadImage
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class MovieDetailFragment : DaggerFragment() {
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)

        arguments?.let { bundle ->
            return@let (bundle.getParcelable(EXTRA_MOVIE) as? Movie)?.let {
                movie = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arrangeToolbar()
    }

    private fun initView() {
        movie.url?.let { movieImage.loadImage(url = it) }
        movieTitle.text = movie.title
        movieOverview.text = movie.overview

    }

    private fun arrangeToolbar() {
        requireActivity().toolbar.title = ""
        requireActivity().toolbar.setNavigationIcon(R.drawable.ic_action_back)
        requireActivity().toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    companion object {
        val TAG: String = MovieDetailFragment::class.java.simpleName
        const val EXTRA_MOVIE = "Movie"

        fun newInstance(movie: Movie): MovieDetailFragment =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_MOVIE, movie)
                }
            }
    }

}

