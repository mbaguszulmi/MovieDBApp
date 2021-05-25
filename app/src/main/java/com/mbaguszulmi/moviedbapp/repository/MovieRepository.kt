package com.mbaguszulmi.moviedbapp.repository

import com.mbaguszulmi.moviedbapp.api.MovieApi
import com.mbaguszulmi.moviedbapp.model.network.Movie

class MovieRepository {
    private val movieApi = MovieApi.getService()

    suspend fun getMovies(): List<Movie> {
        val movieDiscoverResponse = movieApi.discoverMovies(MovieApi.apiKey)

        if (movieDiscoverResponse.isSuccessful) {
            return movieDiscoverResponse.body()!!.results
        }
        return ArrayList()
    }

    suspend fun getMovie(id: Int): Movie? {
        val movieResponse = movieApi.getMovieById(id, MovieApi.apiKey)

        if (movieResponse.isSuccessful) {
            return movieResponse.body()!!
        }
        return null
    }
}
