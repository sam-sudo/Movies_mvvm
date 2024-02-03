package com.hoopCarpool.di

import com.hoopCarpool.movies.data.remote.MoviesApi
import com.hoopCarpool.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MoviesApi{
        return Retrofit.Builder()
            .baseUrl(Constants.API_URL_MOVIES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }


}