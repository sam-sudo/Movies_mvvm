package com.hoopCarpool.movies.model


import com.google.gson.annotations.SerializedName

data class MovieFromSearch(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val imagePath: String,
    var imageUrl: String,
    var backdrop_pathUrl: String,

    )