package com.firassobaid.shutterapp.core.di

import com.firassobaid.shutterapp.data.repository.DefaultMovieRepository
import com.firassobaid.shutterapp.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindMovieRepository(repository: DefaultMovieRepository): MovieRepository
}