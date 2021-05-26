package com.mbaguszulmi.moviedbapp.helper

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import com.mbaguszulmi.moviedbapp.RecyclerViewMatcher
import org.hamcrest.Matcher


object Helper {
    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    fun getText(matcher: ViewInteraction, id: Int): String? {
        val text = arrayOfNulls<String>(1)
        val va: ViewAction = object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController?, view: View) {
                val tv = view.findViewById<TextView>(id)
                text[0] = tv.text.toString()
            }
        }
        matcher.perform(va)
        return text[0]
    }

    fun getRatingBarValue(matcher: ViewInteraction): Float {
        var rating = 0f
        val va: ViewAction = object: ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(RatingBar::class.java)
            }

            override fun getDescription(): String {
                return "Rating value from RatingBar"
            }

            override fun perform(uiController: UiController?, view: View?) {
                val rbar = view as RatingBar
                rating = rbar.rating
            }
        }

        matcher.perform(va)
        return rating
    }
}