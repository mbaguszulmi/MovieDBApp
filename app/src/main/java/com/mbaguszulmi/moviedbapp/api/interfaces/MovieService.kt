package com.mbaguszulmi.moviedbapp.api.interfaces

import com.mbaguszulmi.moviedbapp.model.network.Movie
import com.mbaguszulmi.moviedbapp.model.network.MovieDiscover
import com.mbaguszulmi.moviedbapp.model.network.TV
import com.mbaguszulmi.moviedbapp.model.network.TVDiscover
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<Movie>

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String
    ): Response<MovieDiscover>

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieDiscover>

    @GET("tv/{tv_id}")
    suspend fun getTVById(
        @Path("tv_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<TV>

    @GET("discover/tv")
    suspend fun discoverTVs(
        @Query("api_key") apiKey: String
    ): Response<TVDiscover>

    @GET("discover/tv")
    suspend fun discoverTVs(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<TVDiscover>
}