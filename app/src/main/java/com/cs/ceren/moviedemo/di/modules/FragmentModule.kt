package com.cs.ceren.moviedemo.di.modules

import com.cs.ceren.moviedemo.presentation.view.fragment.MovieDetailFragment
import com.cs.ceren.moviedemo.presentation.view.fragment.MovieListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun provideListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun provideDetailFragment(): MovieDetailFragment

}
