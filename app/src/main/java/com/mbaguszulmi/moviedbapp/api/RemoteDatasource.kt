package com.mbaguszulmi.moviedbapp.api

import com.mbaguszulmi.moviedbapp.api.interfaces.MovieService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteDatasource {
    private const val movieServiceUrl = "https://api.themoviedb.org/3/"
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

    private fun getRetrofit(baseUrl: String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createMovieService(): MovieService {
        return getRetrofit(movieServiceUrl).create(MovieService::class.java)
    }
}
