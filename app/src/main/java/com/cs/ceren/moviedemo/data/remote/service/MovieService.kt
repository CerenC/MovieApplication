package com.cs.ceren.moviedemo.data.remote.service

import com.cs.ceren.moviedemo.data.remote.model.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/now_playing")
    fun getMovies(@Query("page") page: Int = 1): Deferred<MoviesResponse>

    @GET("search/movie")
    fun searchMovies(
        @Query("page") page: Int = 1,
        @Query("query") query: String
    ): Deferred<MoviesResponse>
}

