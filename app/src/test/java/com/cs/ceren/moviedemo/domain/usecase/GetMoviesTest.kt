package com.cs.ceren.moviedemo.domain.usecase

import com.cs.ceren.moviedemo.UnitTest
import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.data.repositories.MovieRepositoryImp
import com.cs.ceren.moviedemo.domain.model.Movie
import com.cs.ceren.moviedemo.domain.repositories.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*


class GetMoviesTest : UnitTest() {

    private lateinit var getMovies: GetMovies

    @Mock
    private lateinit var moviesRepository: MovieRepository

    @Before
    fun setUp() {
        runBlocking {
            getMovies = GetMovies(moviesRepository)
        }
    }

    @Test
    fun `should get data from repository`() {
        runBlocking {
            `when`(moviesRepository.getMovies()).thenReturn(ResultState.Success(emptyList()))
            getMovies.execute()
            verify(moviesRepository).getMovies()
            verifyNoMoreInteractions(moviesRepository)
        }
    }
}
