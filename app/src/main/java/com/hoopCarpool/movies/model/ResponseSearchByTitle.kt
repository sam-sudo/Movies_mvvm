package com.hoopCarpool.movies.model


import com.google.gson.annotations.SerializedName

data class ResponseSearchByTitle(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieFromSearch>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)