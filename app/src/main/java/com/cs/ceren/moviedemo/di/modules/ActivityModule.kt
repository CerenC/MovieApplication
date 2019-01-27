package com.cs.ceren.moviedemo.di.modules

import com.cs.ceren.moviedemo.presentation.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun providesMainActivity(): MainActivity

}