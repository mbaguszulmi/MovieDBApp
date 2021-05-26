package com.mbaguszulmi.moviedbapp.view.activity

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mbaguszulmi.moviedbapp.R
import com.mbaguszulmi.moviedbapp.helper.Helper.getRatingBarValue
import com.mbaguszulmi.moviedbapp.helper.Helper.getText
import com.mbaguszulmi.moviedbapp.helper.Helper.withRecyclerView
import com.mbaguszulmi.moviedbapp.model.database.entities.Genre
import com.mbaguszulmi.moviedbapp.model.network.Movie
import com.mbaguszulmi.moviedbapp.model.network.getGenresStr
import com.mbaguszulmi.moviedbapp.model.network.getRating
import com.mbaguszulmi.moviedbapp.view.adapter.MovieTVListAdapter
import org.hamcrest.Matchers.not
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

const val TAG = "MainActivityTest"

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    // Menampilkan MainActivity
    //// Pastikan memiliki tab layout
    //// Pastikan dapat berpindah ke movies atau tvs
    //// Pastikan ketika berpindah ke tab movies, terdapat elemen dengan id R.id.rv_movies
    //// Pastikan ketika berpindah ke tab tvs, terdapat elemen dengan id R.id.rv_tvs
    //// Pastikan di dalam tab movies, terdapat 20 item di dalam recycler view dengan id R.id.rv_movies
    //// Pastikan di dalam tab tvs, terdapat 20 item di dalam recycler view dengan id R.id.rv_tvs
    //// Pastikan tiap - tiap navigasi ke detail dari item movie atau tv menampilkan data yang sama (dapat diperiksa dari judul yang sama)
    @Test
    fun checkItemSize() {
        Thread.sleep(10000)

        // check if change to movies tab is working
        onView(withText(R.string.tab_movies)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))

        // check if there's 20 items
        for(i in 0..19) {
            onView(withId(R.id.rv_movies))
                .perform(scrollToPosition<MovieTVListAdapter.ViewHolder>(i))

            val text = getText(onView(withRecyclerView(R.id.rv_movies).atPosition(i)), R.id.tv_title)
            onView(withRecyclerView(R.id.rv_movies).atPosition(i))
                .check(matches(hasDescendant(withId(R.id.tv_title))))
                .perform(click())
            Log.d(TAG, "Current movie Title $i = $text")

            Thread.sleep(1000)

            onView(withId(R.id.tv_title)).check(matches(withText(text)))

            pressBack()
        }

        // check if change to movies tab is working
        onView(withText(R.string.tab_tvs)).perform(click())
        onView(withId(R.id.rv_tvs)).check(matches(isDisplayed()))

        // check if there's 20 items
        for(i in 0..19) {
            onView(withId(R.id.rv_tvs))
                .perform(scrollToPosition<MovieTVListAdapter.ViewHolder>(i))

            val text = getText(onView(withRecyclerView(R.id.rv_tvs).atPosition(i)), R.id.tv_title)
            onView(withRecyclerView(R.id.rv_tvs).atPosition(i))
                .check(matches(hasDescendant(withId(R.id.tv_title))))
                .perform(click())
            Log.d(TAG, "Current tv Title $i = $text")

            Thread.sleep(1000)

            onView(withId(R.id.tv_title)).check(matches(withText(text)))

            pressBack()
        }

        // back to movies tab
        onView(withText(R.string.tab_movies)).perform(click())
        onView(withId(R.id.rv_movies))
            .perform(scrollToPosition<MovieTVListAdapter.ViewHolder>(0))
    }
}