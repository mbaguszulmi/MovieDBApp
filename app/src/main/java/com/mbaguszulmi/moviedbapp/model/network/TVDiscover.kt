package com.mbaguszulmi.moviedbapp.model.network

import com.google.gson.annotations.SerializedName

data class TVDiscover(
    val page: Int,
    val results: List<TV>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
