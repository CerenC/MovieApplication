package com.cs.ceren.moviedemo.presentation.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.Toast
import com.cs.ceren.moviedemo.R
import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.di.viewmodel.ViewModelFactory
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.presentation.view.adapter.MovieAdapter
import com.cs.ceren.moviedemo.presentation.viewmodel.MoviesViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject


class MovieListFragment : DaggerFragment() {
    companion object {
        fun newInstance() = MovieListFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewmodel: MoviesViewModel
    lateinit var movieListAdapter: MovieAdapter
    lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(com.cs.ceren.moviedemo.R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)

        setHasOptionsMenu(true)
        initViews()
        loadMovies()
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
                    searchMovie(newText)
                } else {
                    loadMovies()
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
        val spanCount = resources.getInteger(com.cs.ceren.moviedemo.R.integer.span_count);
        movieListAdapter = MovieAdapter(requireActivity())

        movieList.layoutManager = GridLayoutManager(context, spanCount)
        movieList.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount,
                resources.getDimension(com.cs.ceren.moviedemo.R.dimen.grid_spacing),
                true
            )
        )
        movieList.adapter = movieListAdapter
    }

    private fun loadMovies(): Boolean {
        viewmodel.loadMovies()
        subscribeUi()
        return false
    }

    private fun searchMovie(newText: String) {
        viewmodel.searchMovies(newText)
        subscribeUi()
    }


    private fun subscribeUi() {
        viewmodel.movies.observe(this, Observer {
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
        val toast = Toast.makeText(context, e.message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
        Log.d("Error:", e.message)
    }

    inner class GridSpacingItemDecoration(
        private val spanCount: Int,
        spacing: Float,
        private val includeEdge: Boolean
    ) :
        RecyclerView.ItemDecoration() {
        private val spacing: Int = spacing.toInt()

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing /
                        spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }
}