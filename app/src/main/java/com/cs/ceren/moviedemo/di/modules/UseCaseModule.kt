package com.cs.ceren.moviedemo.di.modules

import com.cs.ceren.moviedemo.domain.repositories.MovieRepository
import com.cs.ceren.moviedemo.domain.usecase.GetMoviesUseCase
import com.cs.ceren.moviedemo.domain.usecase.GetSearchMovieListUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetMovie(repository: MovieRepository): GetMoviesUseCase =
        GetMoviesUseCase(repository)

    @Singleton
    @Provides
    fun provideGetSearchMovieListe(repository: MovieRepository): GetSearchMovieListUseCase =
        GetSearchMovieListUseCase(repository)
}