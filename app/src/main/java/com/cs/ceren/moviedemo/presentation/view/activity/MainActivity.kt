package com.cs.ceren.moviedemo.presentation.view.activity

import android.os.Bundle
import com.cs.ceren.moviedemo.R
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.presentation.view.adapter.MovieAdapter
import com.cs.ceren.moviedemo.presentation.view.fragment.MovieDetailFragment
import com.cs.ceren.moviedemo.presentation.view.fragment.MovieListFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : DaggerAppCompatActivity(), MovieAdapter.ItemTouchListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.cs.ceren.moviedemo.R.layout.activity_main)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(com.cs.ceren.moviedemo.R.id.container, MovieListFragment.newInstance())
                .commit()
        }
    }

    override fun onItemClick(Movie: Movie) {
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
