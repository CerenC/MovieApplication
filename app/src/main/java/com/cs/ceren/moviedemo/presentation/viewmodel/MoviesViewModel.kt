package com.cs.ceren.moviedemo.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.domain.usecase.GetMoviesUseCase
import com.cs.ceren.moviedemo.domain.usecase.GetSearchMovieListUseCase
import com.cs.ceren.moviedemo.presentation.SingleLiveEvent
import com.cs.ceren.moviedemo.presentation.events.MoviesEvents
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getSearchMovieListUseCase: GetSearchMovieListUseCase
) : BaseViewModel() {
    private val events = SingleLiveEvent<MoviesEvents>()
    private val moviesLiveData = MutableLiveData<ResultState<List<Movie>>>()

    val movies: LiveData<ResultState<List<Movie>>>
        get() = moviesLiveData

    val moviesEvents: LiveData<MoviesEvents>
        get() = events

    fun loadMovies() {
        launch {
            moviesLiveData.value = getMoviesUseCase.execute()
        }
    }

    fun searchMovies(query: String) {
        launch {
            moviesLiveData.value = getSearchMovieListUseCase.execute(query)
        }
    }

    fun showDetails(movie: Movie) {
        events.value = MoviesEvents.ShowDetail(movie)
    }

}