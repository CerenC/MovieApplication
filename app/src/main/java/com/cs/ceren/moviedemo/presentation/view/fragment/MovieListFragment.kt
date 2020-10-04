package com.cs.ceren.moviedemo.presentation.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.Toast
import com.cs.ceren.moviedemo.R
import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.di.viewmodel.ViewModelFactory
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.presentation.decoration.GridSpacingItemDecoration
import com.cs.ceren.moviedemo.presentation.view.adapter.MovieAdapter
import com.cs.ceren.moviedemo.presentation.viewmodel.MoviesViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MovieListFragment : DaggerFragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MoviesViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(MoviesViewModel::class.java)

        viewModel.loadMovies()
        subscribeUi()
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)

        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE;
        searchView.setOnCloseListener { loadMovies() }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = true
            override fun onQueryTextChange(newText: String): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.searchMovies(newText)
                } else {
                    viewModel.loadMovies()
                }
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        val spanCount = resources.getInteger(R.integer.span_count)

        movieAdapter = MovieAdapter { movie ->
            showMovieDetails(movie)
        }

        movieList.layoutManager = GridLayoutManager(context, spanCount)
        movieList.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount,
                resources.getDimension(R.dimen.grid_spacing),
                true
            )
        )
        movieList.adapter = movieAdapter
    }

    private fun showMovieDetails(movie: Movie) {
        viewModel.showDetails(movie)
    }

    private fun loadMovies(): Boolean {
        viewModel.loadMovies()
        return false
    }

    private fun subscribeUi() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Success -> handleSuccess(it.data)
                is ResultState.Error -> handleError(it.exception)
            }
        })
    }

    private fun handleSuccess(movies: List<Movie>) {
        val adapter = movieList.adapter as MovieAdapter
        adapter.setItems(movies)
        adapter.notifyDataSetChanged()
    }

    private fun handleError(e: Throwable) {
        showErrorMessage(e)

        Log.d("Error:", e.message)
    }

    private fun showErrorMessage(e: Throwable) {
        val toast = Toast.makeText(context, e.message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    companion object {
        fun newInstance() = MovieListFragment()
    }
}