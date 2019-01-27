package com.cs.ceren.moviedemo.di.modules

import com.cs.ceren.moviedemo.domain.repositories.MovieRepository
import com.cs.ceren.moviedemo.domain.usecase.GetMovies
import com.cs.ceren.moviedemo.domain.usecase.GetSearchMovieList
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetMovie(repository: MovieRepository): GetMovies = GetMovies(repository)

    @Singleton
    @Provides
    fun provideGetSearchMovieListe(repository: MovieRepository): GetSearchMovieList = GetSearchMovieList(repository)
}