package com.cs.ceren.moviedemo.domain.usecase

import com.cs.ceren.moviedemo.UnitTest
import com.cs.ceren.moviedemo.data.ResultState
import com.cs.ceren.moviedemo.domain.repositories.MovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*


class GetMoviesUseCaseTest : UnitTest() {

    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Mock
    private lateinit var moviesRepository: MovieRepository

    @Before
    fun setUp() {
        runBlocking {
            getMoviesUseCase = GetMoviesUseCase(moviesRepository)
        }
    }

    @Test
    fun `should get data from repository`() {
        runBlocking {
            `when`(moviesRepository.getMovies()).thenReturn(ResultState.Success(emptyList()))
            getMoviesUseCase.execute()
            verify(moviesRepository).getMovies()
            verifyNoMoreInteractions(moviesRepository)
        }
    }
}
