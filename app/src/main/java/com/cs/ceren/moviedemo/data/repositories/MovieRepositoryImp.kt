package com.cs.ceren.moviedemo.data.repositories

import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.data.remote.model.toMovie
import com.cs.ceren.moviedemo.data.remote.service.MovieService
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.domain.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(val movieService: MovieService) : MovieRepository {
    override suspend fun getMovies(): ResultState<List<Movie>> = withContext(IO) {
        try {
            val response = movieService.getMovies().await()
            ResultState.Success(response.results.map { it.toMovie() })
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    override suspend fun getSearchedMovies(query: String): ResultState<List<Movie>> =
        withContext(IO) {
            try {
                val response = movieService.searchMovies(query = query).await()
                ResultState.Success(response.results.map { it.toMovie() })
            } catch (e: Exception) {
                ResultState.Error(e)
            }
        }

}