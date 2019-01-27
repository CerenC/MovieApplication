package com.cs.ceren.moviedemo.domain.repositories

import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies () : ResultState<List<Movie>>
    suspend fun getSearchMovie(query: String) : ResultState<List<Movie>>
}