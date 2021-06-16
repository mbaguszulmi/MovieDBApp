package com.mbaguszulmi.moviedbapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbaguszulmi.moviedbapp.api.RemoteDatasource
import com.mbaguszulmi.moviedbapp.model.database.entities.Genre
import com.mbaguszulmi.moviedbapp.model.network.Movie
import junit.framework.TestCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.math.abs

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        val movieService = RemoteDatasource.createMovieService()
        movieRepository = MovieRepository(movieService)
    }

    @Test
    fun getMovies() {
        GlobalScope.launch {
            val movieList = movieRepository.getMovies()

            assertNotNull(movieList)
            assertTrue(movieList.isNotEmpty())
        }
    }

    @Test
    fun getMovie() {
        GlobalScope.launch {
            val expectedMovieId = 578701
            val expectedMovie = Movie(expectedMovieId, false,
                "/ouOojiypBE6CD1aqcHPVq7cJf2R.jpg", listOf(
                    Genre(id=53, name="Thriller"),
                    Genre(id=18, name="Drama"), Genre(id=28, name="Action"),
                    Genre(id=9648, name="Mystery")
                ),
                "https://www.warnerbros.com/movies/those-who-wish-me-dead", "tt3215824",
                "en", "Those Who Wish Me Dead", "A young boy finds " +
                        "himself pursued by two assassins in the Montana wilderness with a survival " +
                        "expert determined to protecting him - and a forest fire threatening to consume " +
                        "them all.", 1482.25, "/xCEg6KowNISWvMh8GvPSxtdf9TO.jpg",
                "2021-05-05", 100, "Released", "Nature finds a way.",
                "Those Who Wish Me Dead", 7.0, 396)

            val fetchedMovie = movieRepository.getMovie(expectedMovieId)

            // check if fetchedMovie is not null
            TestCase.assertNotNull(fetchedMovie)
            // check all property in fetchedMovie
            TestCase.assertEquals(fetchedMovie?.id, expectedMovie.id)
            TestCase.assertEquals(fetchedMovie?.adult, expectedMovie.adult)
            TestCase.assertEquals(fetchedMovie?.genres, expectedMovie.genres)
            TestCase.assertEquals(fetchedMovie?.homepage, expectedMovie.homepage)
            TestCase.assertEquals(fetchedMovie?.imdbId, expectedMovie.imdbId)
            TestCase.assertEquals(fetchedMovie?.originalLanguage, expectedMovie.originalLanguage)
            TestCase.assertEquals(fetchedMovie?.originalTitle, expectedMovie.originalTitle)
            TestCase.assertEquals(fetchedMovie?.overview, expectedMovie.overview)
            TestCase.assertEquals(fetchedMovie?.posterPath, expectedMovie.posterPath)
            TestCase.assertEquals(fetchedMovie?.releaseDate, expectedMovie.releaseDate)
            TestCase.assertEquals(fetchedMovie?.runtime, expectedMovie.runtime)
            TestCase.assertEquals(fetchedMovie?.status, expectedMovie.status)
            TestCase.assertEquals(fetchedMovie?.tagline, expectedMovie.tagline)
            TestCase.assertEquals(fetchedMovie?.title, expectedMovie.title)
            TestCase.assertTrue(abs(fetchedMovie!!.voteAverage - expectedMovie.voteAverage) < 0.15)
        }
    }
}