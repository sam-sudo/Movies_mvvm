package com.hoopCarpool.di

import com.hoopCarpool.movies.data.repository.MoviesRepositoryImpl
import com.hoopCarpool.movies.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

}