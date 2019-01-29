package com.cs.ceren.moviedemo.presentation.viewmodel

import com.cs.ceren.moviedemo.AndroidTest
import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.domain.usecase.GetMovies
import com.cs.ceren.moviedemo.domain.usecase.GetSearchMovieList
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class MoviesViewModelTest : AndroidTest() {

    private lateinit var moviesViewModel: MoviesViewModel

    @Mock
    private lateinit var getMovies: GetMovies
    @Mock
    private lateinit var getSearchMovieList: GetSearchMovieList

    @Before
    fun setUp() {
        moviesViewModel = MoviesViewModel(getMovies, getSearchMovieList)
    }

    @Test
    fun `loading movies should update live data`() {
        val moviesList = listOf(Movie(11, "", "Amelie", "Amelie"), Movie(12, "", "Batman", "Batman"))
        runBlocking {
            `when`(getMovies.execute()).thenReturn(ResultState.Success(moviesList))
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