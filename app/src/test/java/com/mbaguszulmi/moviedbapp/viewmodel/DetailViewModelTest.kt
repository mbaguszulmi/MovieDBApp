package com.mbaguszulmi.moviedbapp.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbaguszulmi.moviedbapp.model.database.entities.Genre
import com.mbaguszulmi.moviedbapp.model.network.Movie
import com.mbaguszulmi.moviedbapp.model.network.TV
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

        detailViewModel = DetailViewModel()
    }

    @Test
    fun testGetMovie() {
        val expectedMovieId = 578701
        val expectedMovie = Movie(expectedMovieId, false,
            "/iDdpiUnCeXvBmrkBFpL6lKsZt33.jpg", listOf(Genre(id=53, name="Thriller"),
                Genre(id=18, name="Drama"), Genre(id=28, name="Action"),
                Genre(id=9648, name="Mystery")),
            "https://www.warnerbros.com/movies/those-who-wish-me-dead", "tt3215824",
            "en", "Those Who Wish Me Dead", "A young boy finds " +
                    "himself pursued by two assassins in the Montana wilderness with a survival " +
                    "expert determined to protecting him - and a forest fire threatening to consume " +
                    "them all.", 2406.716, "/xCEg6KowNISWvMh8GvPSxtdf9TO.jpg",
            "2021-05-05", 100, "Released", "Nature finds a way.",
            "Those Who Wish Me Dead", 7.1, 279)

        detailViewModel.getMovie(instrumentationContext, expectedMovieId) {
            // Do Nothing on failure
        }

        // add 5 secs delay to fetch movie from api
        Thread.sleep(5000)

        val fetchedMovie = detailViewModel.movieValue

        // check if fetchedMovie is not null
        assertNotNull(fetchedMovie)
        // check all property in fetchedMovie
        assertEquals(fetchedMovie, expectedMovie)
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
            1257.949, "/o7uk5ChRt3quPIv8PcvPfzyXdMw.jpg", "Returning Series",
            "", "Scripted", 7.8, 704)

        detailViewModel.getTV(instrumentationContext, expectedTVId) {
            // Do Nothing on failure
        }

        // add 5 secs delay to fetch TV show from api
        Thread.sleep(5000)

        val fetchedTV = detailViewModel.tvValue

        // check if fetchedTV is not null
        assertNotNull(fetchedTV)
        // check all property in fetchedTV
        assertEquals(fetchedTV, expectedTV)
    }
}
