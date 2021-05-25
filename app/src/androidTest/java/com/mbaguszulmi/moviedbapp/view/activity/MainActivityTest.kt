package com.mbaguszulmi.moviedbapp.view.activity

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mbaguszulmi.moviedbapp.R
import com.mbaguszulmi.moviedbapp.helper.Helper.getText
import com.mbaguszulmi.moviedbapp.helper.Helper.withRecyclerView
import com.mbaguszulmi.moviedbapp.view.adapter.MovieTVListAdapter
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

const val TAG = "MainActivityTest"

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    // TESTING SCENARIO
    //
    // Open MainActivity
    // Wait for 10 seconds to load movies and tv show
    // click on movies tab and check if movie list exist
    // click on 1st item on movie list -> check title on movie details.
    // click on 2nd item on movie list -> check title on movie details.
    // ...
    // click on 20th item on movie list -> check title on movie details.
    // click on tvs tab and check if tv list exist
    // click on 1st item on tv list -> check title on tv details.
    // click on 2nd item on tv list -> check title on tv details.
    // ...
    // click on 20th item on tv list -> check title on tv details.

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

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