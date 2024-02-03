package com.hoopCarpool.movies.providers.services

import com.hoopCarpool.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.API_URL_MOVIES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}



