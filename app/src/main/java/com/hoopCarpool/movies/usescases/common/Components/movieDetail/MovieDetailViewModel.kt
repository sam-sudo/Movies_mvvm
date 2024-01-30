package com.hoopCarpool.movies.usescases.common.Components.movieDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieDetailViewModel :ViewModel(){


    val favoriteStatus = MutableLiveData<Boolean>()


}