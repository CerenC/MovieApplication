package com.cs.ceren.moviedemo.data.repositories

import com.cs.ceren.moviedemo.UnitTest
import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.data.remote.model.MovieItem
import com.cs.ceren.moviedemo.data.remote.model.MoviesResponse
import com.cs.ceren.moviedemo.data.remote.model.toMovie
import com.cs.ceren.moviedemo.data.remote.service.MovieService
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*

class MovieRepositoryImpTest : UnitTest() {
    private lateinit var movieRepository: MovieRepositoryImp

    @Mock
    private lateinit var service: MovieService
    @Mock
    private lateinit var response: MoviesResponse

    @Before
    fun setUp() {
        movieRepository = MovieRepositoryImp(service)
    }

    @Test
    fun `get movie empty list`() {
        response = MoviesResponse(1, emptyList(), 42, 100)
        runBlocking {
            `when`(service.getMovies()).thenReturn(async { response })
            val movies = movieRepository.getMovies()
            assertThat(movies, instanceOf(ResultState.Success::class.java))
            if (movies is ResultState.Success) {
                assertThat(movies.data, `is`(emptyList()))
            }
            verify(service).getMovies()
        }
    }

    @Test
    fun `get movie list from service success`() {
        response = MoviesResponse(1, listOf(MovieItem("", "Life is Beautiful", 1, "Life is Beautiful")), 42, 100)
        runBlocking {
            `when`(service.getMovies()).thenReturn(async { response })
            val movies = movieRepository.getMovies()
            verify(service).getMovies()
            assertThat(movies, instanceOf(ResultState.Success::class.java))
            if (movies is ResultState.Success) {
                val movieList = response.results.map { (it as MovieItem).toMovie() }
                assertThat(movies.data, `is`(movieList))
            }
        }
    }

    @Test
    fun `get movie list from service fail`() {
        runBlocking {
            `when`(service.getMovies(0)).thenReturn(async { response })
            val movies = movieRepository.getMovies()
            verify(service).getMovies()
            assertThat(movies, instanceOf(ResultState.Error::class.java))
        }
    }

    @Test
    fun `get movies called only once`() {
        `when`(service.getMovies(ArgumentMatchers.anyInt())).thenReturn(null)
        runBlocking { movieRepository.getMovies() }
        verify(service, times(1)).getMovies()

    }

    @Test
    fun `search movies called only once`() {
        `when`(service.searchMovies(1, "LOTR")).thenReturn(null)
        runBlocking { movieRepository.getSearchedMovies("LOTR") }
        verify(service, times(1)).searchMovies(1, "LOTR")
    }
}



