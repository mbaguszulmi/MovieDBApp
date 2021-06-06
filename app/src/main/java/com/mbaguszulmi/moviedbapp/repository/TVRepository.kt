package com.mbaguszulmi.moviedbapp.repository

import com.mbaguszulmi.moviedbapp.api.RemoteDatasource
import com.mbaguszulmi.moviedbapp.api.interfaces.MovieService
import com.mbaguszulmi.moviedbapp.model.network.TV

class TVRepository(
    private val movieService: MovieService
) {

    suspend fun getTVs(): List<TV> {
        val tvDiscoverResponse = movieService.discoverTVs(RemoteDatasource.apiKey)

        if (tvDiscoverResponse.isSuccessful) {
            return tvDiscoverResponse.body()!!.results
        }
        return ArrayList()
    }

    suspend fun getTV(id: Int): TV? {
        val tvResponse = movieService.getTVById(id, RemoteDatasource.apiKey)

        if (tvResponse.isSuccessful) {
            return tvResponse.body()!!
        }
        return null
    }
}