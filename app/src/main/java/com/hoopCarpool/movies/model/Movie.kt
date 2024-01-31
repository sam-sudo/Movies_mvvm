package com.hoopCarpool.movies.model

import com.google.gson.annotations.SerializedName
import com.hoopCarpool.movies.util.Constants

data class Movie(
    val id: String,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val imagePath: String,
    var imageUrl: String,
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    var backdrop_pathUrl: String,
    @SerializedName("vote_average")
    val stars: Double,
    @SerializedName("release_date")
    val date: String,
    @SerializedName("runtime")
    val duration: Double,
    var favorite: Boolean = false
)