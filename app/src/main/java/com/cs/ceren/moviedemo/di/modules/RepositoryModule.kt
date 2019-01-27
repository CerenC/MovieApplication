package com.cs.ceren.moviedemo.di.modules

import com.cs.ceren.moviedemo.data.remote.service.MovieService
import com.cs.ceren.moviedemo.data.repositories.MovieRepositoryImp
import com.cs.ceren.moviedemo.domain.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepo(movieService: MovieService) : MovieRepository = MovieRepositoryImp (movieService)
}