package com.mbaguszulmi.moviedbapp.model.network

import com.google.gson.annotations.SerializedName

data class MovieDiscover(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
