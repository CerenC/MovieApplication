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
    companion object {
        val TAG = MovieDetailFragment::class.java.simpleName
        val MOVIE = "Movie"

        fun newInstance(movie: Movie): MovieDetailFragment {
            val fragmentInstance = MovieDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(MOVIE, movie)
            fragmentInstance.arguments = bundle
            return fragmentInstance
        }

    }

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        arguments?.let {
            return@let (it.getParcelable(MOVIE) as? Movie)?.let {
                movie = it
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

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


}