package com.mbaguszulmi.moviedbapp.repository

import com.mbaguszulmi.moviedbapp.api.RemoteDatasource
import com.mbaguszulmi.moviedbapp.api.interfaces.MovieService
import com.mbaguszulmi.moviedbapp.model.network.Movie

class MovieRepository(
    private val movieService: MovieService
) {

    suspend fun getMovies(): List<Movie> {
        val movieDiscoverResponse = movieService.discoverMovies(RemoteDatasource.apiKey)

        if (movieDiscoverResponse.isSuccessful) {
            return movieDiscoverResponse.body()!!.results
        }
        return ArrayList()
    }

    suspend fun getMovie(id: Int): Movie? {
        val movieResponse = movieService.getMovieById(id, RemoteDatasource.apiKey)

        if (movieResponse.isSuccessful) {
            return movieResponse.body()!!
        }
        return null
    }
}
