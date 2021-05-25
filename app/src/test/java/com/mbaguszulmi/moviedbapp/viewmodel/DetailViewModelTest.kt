package com.mbaguszulmi.moviedbapp.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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

        detailViewModel.getMovie(instrumentationContext, expectedMovieId) {
            // Do Nothing on failure
        }

        // add 5 secs delay to fetch movie from api
        Thread.sleep(5000)

        val fetchedMovie = detailViewModel.movieValue
        assertNotNull(fetchedMovie)
        // check if fetchedMovie.id = 578701
        assertEquals(fetchedMovie?.id, expectedMovieId)
    }

    @Test
    fun testGetTV() {
        val expectedTVId = 120168

        detailViewModel.getTV(instrumentationContext, expectedTVId) {
            // Do Nothing on failure
        }

        // add 5 secs delay to fetch TV show from api
        Thread.sleep(5000)

        val fetchedTV = detailViewModel.tvValue
        assertNotNull(fetchedTV)
        // check if fetchedTV.id = 120168
        assertEquals(fetchedTV?.id, expectedTVId)
    }
}
