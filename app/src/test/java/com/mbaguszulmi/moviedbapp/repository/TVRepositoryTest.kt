package com.mbaguszulmi.moviedbapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbaguszulmi.moviedbapp.api.RemoteDatasource
import com.mbaguszulmi.moviedbapp.model.database.entities.Genre
import com.mbaguszulmi.moviedbapp.model.network.TV
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
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
class TVRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvRepository: TVRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        val movieService = RemoteDatasource.createMovieService()
        tvRepository = TVRepository(movieService)
    }

    @After
    fun tearDown() {
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTVs() {
        GlobalScope.launch {
            val tvList = tvRepository.getTVs()
            assertNotNull(tvList)
            assertTrue(tvList.isNotEmpty())
        }
    }

    @Test
    fun getTV() {
        GlobalScope.launch {
            val expectedTVId = 120168
            val expectedTV = TV(expectedTVId, "/dYvIUzdh6TUv4IFRq8UBkX7bNNu.jpg",
                listOf(
                    Genre(id=18, name="Drama"), Genre(id=80, name="Crime"),
                    Genre(id=9648, name="Mystery")
                ),
                "https://www.netflix.com/title/81166747", true,
                "2021-05-19", "Who Killed Sara?", 18, 2,
                "es", "¿Quién mató a Sara?", "Hell-bent on exacting " +
                        "revenge and proving he was framed for his sister's murder, Álex sets out " +
                        "to unearth much more than the crime's real culprit.",
                725.655, "/o7uk5ChRt3quPIv8PcvPfzyXdMw.jpg", "Returning Series",
                "", "Scripted", 7.8, 773)

            val fetchedTV = tvRepository.getTV(expectedTVId)

            // check if fetchedTV is not null
            TestCase.assertNotNull(fetchedTV)
            // check all property in fetchedTV
            TestCase.assertEquals(fetchedTV?.id, expectedTV.id)
            TestCase.assertEquals(fetchedTV?.genres, expectedTV.genres)
            TestCase.assertEquals(fetchedTV?.homepage, expectedTV.homepage)
            TestCase.assertEquals(fetchedTV?.inProduction, expectedTV.inProduction)
            TestCase.assertEquals(fetchedTV?.lastAirDate, expectedTV.lastAirDate)
            TestCase.assertEquals(fetchedTV?.name, expectedTV.name)
            TestCase.assertEquals(fetchedTV?.numberOfEpisodes, expectedTV.numberOfEpisodes)
            TestCase.assertEquals(fetchedTV?.numberOfSeasons, expectedTV.numberOfSeasons)
            TestCase.assertEquals(fetchedTV?.originalLanguage, expectedTV.originalLanguage)
            TestCase.assertEquals(fetchedTV?.originalName, expectedTV.originalName)
            TestCase.assertEquals(fetchedTV?.overview, expectedTV.overview)
            TestCase.assertEquals(fetchedTV?.posterPath, expectedTV.posterPath)
            TestCase.assertEquals(fetchedTV?.status, expectedTV.status)
            TestCase.assertEquals(fetchedTV?.tagline, expectedTV.tagline)
            TestCase.assertEquals(fetchedTV?.type, expectedTV.type)
            TestCase.assertTrue(abs(fetchedTV!!.voteAverage - expectedTV.voteAverage) < 0.15)
        }
    }
}