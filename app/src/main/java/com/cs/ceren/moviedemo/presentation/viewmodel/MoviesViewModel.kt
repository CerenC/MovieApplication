package com.cs.ceren.moviedemo.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.domain.usecase.GetMovies
import com.cs.ceren.moviedemo.domain.usecase.GetSearchMovieList
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val getSearchMovieList: GetSearchMovieList
) : BaseViewModel() {
    private val _movies = MutableLiveData<ResultState<List<Movie>>>()
    val movies: LiveData<ResultState<List<Movie>>>
        get() = _movies

    fun loadMovies() {
        launch {
            _movies.value = getMovies.execute()
        }
    }

    fun searchMovies(query: String) {
        launch {
            _movies.value = getSearchMovieList.execute(query)
        }
    }
}