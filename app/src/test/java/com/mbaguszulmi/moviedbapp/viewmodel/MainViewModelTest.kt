package com.mbaguszulmi.moviedbapp.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : TestCase() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    private var mainViewModel: MainViewModel = mock()
    private val instrumentationContext: Context = mock()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        mainViewModel = MainViewModel()
    }

    @Test
    fun testRefreshMovies() {
        val expectedMoviesLength = 20

        mainViewModel.refreshMovies(instrumentationContext)

        // add 5 secs delay to fetch movie list from api
        Thread.sleep(5000)

        val fetchedMovies = mainViewModel.movieList
        assertNotNull(fetchedMovies)
        // check if movie list length is 20
        assertEquals(fetchedMovies.size, expectedMoviesLength)
    }

    @Test
    fun testRefreshTvs() {
        val expectedTVsLength = 20

        mainViewModel.refreshTvs(instrumentationContext)

        // add 5 secs delay to fetch TV show list from api
        Thread.sleep(5000)

        val fetchedTVs = mainViewModel.tvList
        assertNotNull(fetchedTVs)
        // check if tv list length is 20
        assertEquals(fetchedTVs.size, expectedTVsLength)
    }
}