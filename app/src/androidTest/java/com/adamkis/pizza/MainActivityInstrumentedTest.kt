package com.adamkis.pizza

import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.adamkis.pizza.helper.TestUtils
import com.adamkis.pizza.ui.activity.MainActivity
import org.junit.Rule
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.widget.TextView
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @Suppress("unused") // actually used by Espresso
    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun homeActivity_Displayed() {
        onView(withText(TestUtils.getString(R.string.main_title))).check(matches(ViewMatchers.isDisplayed()))
    }

}
