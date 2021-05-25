package com.mbaguszulmi.moviedbapp.repository

import com.mbaguszulmi.moviedbapp.api.MovieApi
import com.mbaguszulmi.moviedbapp.model.network.TV

class TVRepository {
    private val movieApi = MovieApi.getService()

    suspend fun getTVs(): List<TV> {
        val tvDiscoverResponse = movieApi.discoverTVs(MovieApi.apiKey)

        if (tvDiscoverResponse.isSuccessful) {
            return tvDiscoverResponse.body()!!.results
        }
        return ArrayList()
    }

    suspend fun getTV(id: Int): TV? {
        val tvResponse = movieApi.getTVById(id, MovieApi.apiKey)

        if (tvResponse.isSuccessful) {
            return tvResponse.body()!!
        }
        return null
    }
}