package com.mbaguszulmi.moviedbapp.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbaguszulmi.moviedbapp.api.RemoteDatasource
import com.mbaguszulmi.moviedbapp.model.database.entities.Genre
import com.mbaguszulmi.moviedbapp.model.network.Movie
import com.mbaguszulmi.moviedbapp.model.network.TV
import com.mbaguszulmi.moviedbapp.repository.MovieRepository
import com.mbaguszulmi.moviedbapp.repository.TVRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest : TestCase() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private inline fun <reified T> mock(): T = mock(T::class.java)

    private var detailViewModel: DetailViewModel = mock()
    private val instrumentationContext: Context = mock()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        val movieService = RemoteDatasource.createMovieService()
        val movieRepository = MovieRepository(movieService)
        val tvRepository = TVRepository(movieService)
        detailViewModel = DetailViewModel(movieRepository, tvRepository)
    }

    @Test
    fun testGetMovie() {
        val expectedMovieId = 578701
        val expectedMovie = Movie(expectedMovieId, false,
            "/ouOojiypBE6CD1aqcHPVq7cJf2R.jpg", listOf(Genre(id=53, name="Thriller"),
                Genre(id=18, name="Drama"), Genre(id=28, name="Action"),
                Genre(id=9648, name="Mystery")),
            "https://www.warnerbros.com/movies/those-who-wish-me-dead", "tt3215824",
            "en", "Those Who Wish Me Dead", "A young boy finds " +
                    "himself pursued by two assassins in the Montana wilderness with a survival " +
                    "expert determined to protecting him - and a forest fire threatening to consume " +
                    "them all.", 1482.25, "/xCEg6KowNISWvMh8GvPSxtdf9TO.jpg",
            "2021-05-05", 100, "Released", "Nature finds a way.",
            "Those Who Wish Me Dead", 7.0, 394)

        detailViewModel.getMovie(instrumentationContext, expectedMovieId) {
            // Do Nothing on failure
        }

        // add 5 secs delay to fetch movie from api
        Thread.sleep(5000)

        val fetchedMovie = detailViewModel.movieValue

        // check if fetchedMovie is not null
        assertNotNull(fetchedMovie)
        // check all property in fetchedMovie
        assertEquals(fetchedMovie?.id, expectedMovie.id)
        assertEquals(fetchedMovie?.adult, expectedMovie.adult)
        assertEquals(fetchedMovie?.backdropPath, expectedMovie.backdropPath)
        assertEquals(fetchedMovie?.genres, expectedMovie.genres)
        assertEquals(fetchedMovie?.homepage, expectedMovie.homepage)
        assertEquals(fetchedMovie?.imdbId, expectedMovie.imdbId)
        assertEquals(fetchedMovie?.originalLanguage, expectedMovie.originalLanguage)
        assertEquals(fetchedMovie?.originalTitle, expectedMovie.originalTitle)
        assertEquals(fetchedMovie?.overview, expectedMovie.overview)
        assertEquals(fetchedMovie?.popularity, expectedMovie.popularity)
        assertEquals(fetchedMovie?.posterPath, expectedMovie.posterPath)
        assertEquals(fetchedMovie?.releaseDate, expectedMovie.releaseDate)
        assertEquals(fetchedMovie?.runtime, expectedMovie.runtime)
        assertEquals(fetchedMovie?.status, expectedMovie.status)
        assertEquals(fetchedMovie?.tagline, expectedMovie.tagline)
        assertEquals(fetchedMovie?.title, expectedMovie.title)
        assertEquals(fetchedMovie?.voteAverage, expectedMovie.voteAverage)
        assertEquals(fetchedMovie?.voteCount, expectedMovie.voteCount)
    }

    @Test
    fun testGetTV() {
        val expectedTVId = 120168
        val expectedTV = TV(expectedTVId, "/dYvIUzdh6TUv4IFRq8UBkX7bNNu.jpg",
            listOf(Genre(id=18, name="Drama"), Genre(id=80, name="Crime"),
                Genre(id=9648, name="Mystery")),
            "https://www.netflix.com/title/81166747", true,
            "2021-05-19", "Who Killed Sara?", 18, 2,
            "es", "¿Quién mató a Sara?", "Hell-bent on exacting " +
                    "revenge and proving he was framed for his sister's murder, Álex sets out " +
                    "to unearth much more than the crime's real culprit.",
            725.655, "/o7uk5ChRt3quPIv8PcvPfzyXdMw.jpg", "Returning Series",
            "", "Scripted", 7.8, 772)

        detailViewModel.getTV(instrumentationContext, expectedTVId) {
            // Do Nothing on failure
        }

        // add 5 secs delay to fetch TV show from api
        Thread.sleep(5000)

        val fetchedTV = detailViewModel.tvValue

        // check if fetchedTV is not null
        assertNotNull(fetchedTV)
        // check all property in fetchedTV
        assertEquals(fetchedTV?.id, expectedTV.id)
        assertEquals(fetchedTV?.backdropPath, expectedTV.backdropPath)
        assertEquals(fetchedTV?.genres, expectedTV.genres)
        assertEquals(fetchedTV?.homepage, expectedTV.homepage)
        assertEquals(fetchedTV?.inProduction, expectedTV.inProduction)
        assertEquals(fetchedTV?.lastAirDate, expectedTV.lastAirDate)
        assertEquals(fetchedTV?.name, expectedTV.name)
        assertEquals(fetchedTV?.numberOfEpisodes, expectedTV.numberOfEpisodes)
        assertEquals(fetchedTV?.numberOfSeasons, expectedTV.numberOfSeasons)
        assertEquals(fetchedTV?.originalLanguage, expectedTV.originalLanguage)
        assertEquals(fetchedTV?.originalName, expectedTV.originalName)
        assertEquals(fetchedTV?.overview, expectedTV.overview)
        assertEquals(fetchedTV?.popularity, expectedTV.popularity)
        assertEquals(fetchedTV?.posterPath, expectedTV.posterPath)
        assertEquals(fetchedTV?.status, expectedTV.status)
        assertEquals(fetchedTV?.tagline, expectedTV.tagline)
        assertEquals(fetchedTV?.type, expectedTV.type)
        assertEquals(fetchedTV?.voteAverage, expectedTV.voteAverage)
        assertEquals(fetchedTV?.voteCount, expectedTV.voteCount)
    }
}
