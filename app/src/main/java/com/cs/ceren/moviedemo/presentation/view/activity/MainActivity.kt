package com.cs.ceren.moviedemo.presentation.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import com.cs.ceren.moviedemo.R
import com.cs.ceren.moviedemo.di.viewmodel.ViewModelFactory
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.presentation.events.MoviesEvents
import com.cs.ceren.moviedemo.presentation.view.fragment.MovieDetailFragment
import com.cs.ceren.moviedemo.presentation.view.fragment.MovieListFragment
import com.cs.ceren.moviedemo.presentation.viewmodel.MoviesViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieListFragment.newInstance())
                .commit()
        }

        viewModel = ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)
        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.moviesEvents.observe(this, Observer {
            when (it) {
                is MoviesEvents.ShowDetail -> showDetail(it.movie)
            }
        })
    }

    private fun showDetail(Movie: Movie) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MovieDetailFragment.newInstance(Movie))
            .addToBackStack(MovieDetailFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        resetTitle()
    }

    private fun resetTitle() {
        toolbar.title = getString(R.string.app_name)
        toolbar.navigationIcon = null
    }


}
