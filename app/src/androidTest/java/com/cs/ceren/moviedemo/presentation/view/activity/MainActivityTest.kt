package com.cs.ceren.moviedemo.presentation.view.activity

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.cs.ceren.moviedemo.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    private lateinit var stringToBetyped: String

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        stringToBetyped = "Avengers"
    }

    @Test
    fun searchButton_actionCheck() {
        onView(withId(R.id.search_button))
            .perform(click())

        onView(withId(R.id.search_src_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searchText_typeTextCheck() {
        onView(withId(R.id.search_button))
            .perform(click())

        onView(withId(R.id.search_src_text))
            .perform(typeText("Avengers"))
            .perform(click())
            .check(matches(withText("Avengers")))
            .check(matches(isDisplayed()))
    }

    // Movie List Object should be visible at the beginning
    @Test
    fun movieList_isDisplayed_atInitialization() {
        onView(withId(R.id.movieList))
            .check(matches(isDisplayed()))
    }

    //
    @Test
    fun movies_listItemsCheck() {
        /*onView(withId(R.id.movieList))
            .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.movieOverview))
            .check(matches(isDisplayed()))*/
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.cs.ceren.moviedemo", appContext.packageName)
    }
}