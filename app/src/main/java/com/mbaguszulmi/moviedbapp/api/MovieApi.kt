package com.mbaguszulmi.moviedbapp.api

import com.mbaguszulmi.moviedbapp.model.network.Movie
import com.mbaguszulmi.moviedbapp.model.network.MovieDiscover
import com.mbaguszulmi.moviedbapp.model.network.TV
import com.mbaguszulmi.moviedbapp.model.network.TVDiscover
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object MovieApi {
    private const val baseUrl = "https://api.themoviedb.org/3/"
    const val apiKey = "8f89fe6c68e4ceb635258ced9e6477ea"

    private fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder().build()
                chain.proceed(newRequest)
            }
            .build()
    }

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): Service {
        return getRetrofit().create(Service::class.java)
    }

    interface Service {
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
}
