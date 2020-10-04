package com.cs.ceren.moviedemo.presentation.viewmodel

import com.cs.ceren.moviedemo.AndroidTest
import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.domain.usecase.GetMoviesUseCase
import com.cs.ceren.moviedemo.domain.usecase.GetSearchMovieListUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class MoviesViewModelTest : AndroidTest() {

    private lateinit var moviesViewModel: MoviesViewModel

    @Mock
    private lateinit var getMoviesUseCase: GetMoviesUseCase
    @Mock
    private lateinit var getSearchMovieListUseCase: GetSearchMovieListUseCase

    @Before
    fun setUp() {
        moviesViewModel = MoviesViewModel(getMoviesUseCase, getSearchMovieListUseCase)
    }

    @Test
    fun `loading movies should update live data`() {
        val moviesList = listOf(Movie(11, "", "Amelie", "Amelie"), Movie(12, "", "Batman", "Batman"))
        runBlocking {
            `when`(getMoviesUseCase.execute()).thenReturn(ResultState.Success(moviesList))
            runBlocking { moviesViewModel.loadMovies() }
            moviesViewModel.movies.observeForever {
                if (it is ResultState.Success) {
                    assertEquals(it.data!!.size, 2)
                    assertEquals(it.data[0].id, 11)
                    assertEquals(it.data[1].title, "Batman")
                }

            }


        }
    }
}