package com.mbaguszulmi.moviedbapp.view.activity

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mbaguszulmi.moviedbapp.R
import com.mbaguszulmi.moviedbapp.helper.Helper
import com.mbaguszulmi.moviedbapp.model.database.entities.Genre
import com.mbaguszulmi.moviedbapp.model.network.Movie
import com.mbaguszulmi.moviedbapp.model.network.TV
import com.mbaguszulmi.moviedbapp.model.network.getGenresStr
import com.mbaguszulmi.moviedbapp.model.network.getRating
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

@RunWith(AndroidJUnit4ClassRunner::class)
class DetailActivityTest {

    private lateinit var scenario: ActivityScenario<DetailActivity>

    @After
    fun tearDown() {
        scenario.close()
        Intents.release()
    }

    // Menampilkan Detail Movie dengan id 578701
    //// Pastikan elemen R.id.tv_title memiliki teks = expectedMovie.title
    //// Pastikan elemen R.id.tv_genres memiliki teks = expectedMovie.getGenresStr()
    //// Pastikan nilai dari ratingbar memiliki error <= 0.15
    //// Pastikan durasi menit ditampilkan dan memiliki nilai "${expectedMovie.runtime} min"
    //// Pastikan teks eposode tidak ditampilkan
    //// Pastikan teks seasons tidak ditampilkan
    //// Pastikan elemen R.id.tv_date memiliki teks = expectedMovie.releaseDate
    //// Pastikan elemen R.id.tv_overview_content memiliki teks = expectedMovie.overview
    //// Pastikan ketika elemen R.id.btn_visit_home_page diklik, maka activity akan memuat intent dengan action Intent.ACTION_VIEW
    //// Pastikan ketika elemen R.id.btn_share diklik, maka activity akan memuat intent dengan action Intent.ACTION_CHOOSER
    @Test
    fun checkMovieDetails() {
        val expectedMovieId = 578701
        val expectedMovie = Movie(expectedMovieId, false,
            "/iDdpiUnCeXvBmrkBFpL6lKsZt33.jpg", listOf(
                Genre(id=53, name="Thriller"),
                Genre(id=18, name="Drama"), Genre(id=28, name="Action"),
                Genre(id=9648, name="Mystery")
            ),
            "https://www.warnerbros.com/movies/those-who-wish-me-dead", "tt3215824",
            "en", "Those Who Wish Me Dead", "A young boy finds " +
                    "himself pursued by two assassins in the Montana wilderness with a survival " +
                    "expert determined to protecting him - and a forest fire threatening to consume " +
                    "them all.", 2406.716, "/xCEg6KowNISWvMh8GvPSxtdf9TO.jpg",
            "2021-05-05", 100, "Released", "Nature finds a way.",
            "Those Who Wish Me Dead", 7.1, 279)
        val moveIntent = Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.ITEM_ID, expectedMovieId)
            putExtra(DetailActivity.ITEM_TYPE, DetailActivity.ITEM_TYPE_MOVIE)
        }

        scenario = ActivityScenario.launch(moveIntent)
        Intents.init()

        // add 1 secs delay to fetch movie
        Thread.sleep(1000)

        // check movie title
        Espresso.onView(ViewMatchers.withId(R.id.tv_title))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedMovie.title)))
        // check movie genres
        Espresso.onView(ViewMatchers.withId(R.id.tv_genres))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedMovie.getGenresStr())))
        // check rating bar
        val rating =
            Helper.getRatingBarValue(Espresso.onView(ViewMatchers.withId(R.id.rbar_content)))
        assertTrue(abs(rating - expectedMovie.getRating()) <= 0.15)
        // check minutes is displayed and have expected value
        Espresso.onView(ViewMatchers.withId(R.id.tv_durations))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers.withText("${expectedMovie.runtime} min")))
        // check if episodes is not displayed
        Espresso.onView(ViewMatchers.withId(R.id.tv_episodes))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
        // check if seasons is not displayed
        Espresso.onView(ViewMatchers.withId(R.id.tv_seasons))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
        // check released date
        Espresso.onView(ViewMatchers.withId(R.id.tv_date))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedMovie.releaseDate)))
        // check overview
        Espresso.onView(ViewMatchers.withId(R.id.tv_overview_content))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedMovie.overview)))
        // check visit movie page button
        Espresso.onView(ViewMatchers.withId(R.id.btn_visit_home_page))
            .perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
        tearDown()

        Thread.sleep(5000)

        scenario = ActivityScenario.launch(moveIntent)
        Intents.init()

        Thread.sleep(2000)

        // check share button
        Espresso.onView(ViewMatchers.withId(R.id.btn_share))
            .perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_CHOOSER))
    }

    // Menampilkan Detail TV dengan id 120168
    //// Pastikan elemen R.id.tv_title memiliki teks = expectedTV.name
    //// Pastikan elemen R.id.tv_genres memiliki teks = expectedTV.getGenresStr()
    //// Pastikan nilai dari ratingbar memiliki error <= 0.15
    //// Pastikan durasi menit tidak ditampilkan
    //// Pastikan teks eposode ditampilkan dan memiliki nilai "${expectedTV.numberOfEpisodes} episodes"
    //// Pastikan teks seasons ditampilkan dan memiliki nilai "${expectedTV.numberOfSeasons} seasons"
    //// Pastikan elemen R.id.tv_date memiliki teks = expectedTV.lastAirDate
    //// Pastikan elemen R.id.tv_overview_content memiliki teks = expectedTV.overview
    //// Pastikan ketika elemen R.id.btn_visit_home_page diklik, maka activity akan memuat intent dengan action Intent.ACTION_VIEW
    //// Pastikan ketika elemen R.id.btn_share diklik, maka activity akan memuat intent dengan action Intent.ACTION_CHOOSER
    @Test
    fun checkTVDetails() {
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
        val moveIntent = Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.ITEM_ID, expectedTVId)
            putExtra(DetailActivity.ITEM_TYPE, DetailActivity.ITEM_TYPE_TV)
        }

        scenario = ActivityScenario.launch(moveIntent)
        Intents.init()

        // add 1 secs delay to fetch movie
        Thread.sleep(1000)

        // check movie title
        Espresso.onView(ViewMatchers.withId(R.id.tv_title))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedTV.name)))
        // check movie genres
        Espresso.onView(ViewMatchers.withId(R.id.tv_genres))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedTV.getGenresStr())))
        // check rating bar
        val rating =
            Helper.getRatingBarValue(Espresso.onView(ViewMatchers.withId(R.id.rbar_content)))
        assertTrue(abs(rating - expectedTV.getRating()) <= 0.15)
        // check minutes is not displayed
        Espresso.onView(ViewMatchers.withId(R.id.tv_durations))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
        // check if episodes is not displayed
        Espresso.onView(ViewMatchers.withId(R.id.tv_episodes))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers.withText("${expectedTV.numberOfEpisodes} episodes")))
        // check if seasons is not displayed
        Espresso.onView(ViewMatchers.withId(R.id.tv_seasons))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers.withText("${expectedTV.numberOfSeasons} seasons")))
        // check released date
        Espresso.onView(ViewMatchers.withId(R.id.tv_date))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedTV.lastAirDate)))
        // check overview
        Espresso.onView(ViewMatchers.withId(R.id.tv_overview_content))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedTV.overview)))
        // check visit movie page button
        Espresso.onView(ViewMatchers.withId(R.id.btn_visit_home_page))
            .perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
        tearDown()

        Thread.sleep(5000)

        scenario = ActivityScenario.launch(moveIntent)
        Intents.init()

        Thread.sleep(2000)

        // check share button
        Espresso.onView(ViewMatchers.withId(R.id.btn_share))
            .perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_CHOOSER))
    }
}