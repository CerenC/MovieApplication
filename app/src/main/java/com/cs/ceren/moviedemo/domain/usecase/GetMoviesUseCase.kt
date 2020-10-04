package com.cs.ceren.moviedemo.domain.usecase

import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.domain.repositories.MovieRepository

class GetMoviesUseCase constructor(private val movieRepository: MovieRepository) {
    suspend fun execute(): ResultState<List<Movie>> {
        return movieRepository.getMovies()
    }
}