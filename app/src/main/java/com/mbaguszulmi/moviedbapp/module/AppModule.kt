package com.mbaguszulmi.moviedbapp.module

import com.mbaguszulmi.moviedbapp.api.RemoteDatasource
import com.mbaguszulmi.moviedbapp.api.interfaces.MovieService
import com.mbaguszulmi.moviedbapp.repository.MovieRepository
import com.mbaguszulmi.moviedbapp.repository.TVRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieService(): MovieService = RemoteDatasource.createMovieService()

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieService: MovieService
    ): MovieRepository = MovieRepository(movieService)


    @Provides
    @Singleton
    fun provideTVRepository(
        movieService: MovieService
    ): TVRepository = TVRepository(movieService)

}
