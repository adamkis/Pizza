package com.adamkis.pizza

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.adamkis.pizza.helper.MockResources
import com.adamkis.pizza.ui.activity.MainActivity
import com.squareup.okhttp.mockwebserver.Dispatcher
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.squareup.okhttp.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityMockWebServerInstrumentedTest {

    @Suppress("unused") // actually used by Espresso
    @get:Rule
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, true, false)
    lateinit private var server: MockWebServer
    val app by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App }

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when {
                    request.path.contains("ozt3z") -> MockResponse().setResponseCode(200).setBody(MockResources.MOCK_INGREDIENTS)
                    request.path.contains("150da7") -> MockResponse().setResponseCode(200).setBody(MockResources.MOCK_DRINKS)
                    request.path.contains("dokm7") -> MockResponse().setResponseCode(200).setBody(MockResources.MOCK_PIZZAS)
                    else -> MockResponse().setResponseCode(404)
                }
            }
        }
        server.setDispatcher(dispatcher)
        app.setNetComponent(app.createNetComponent(server.url("/").toString()))
        mActivityRule.launchActivity(Intent())
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun mainActivity_isPizzasListFilled() {
        for ((i, pizza) in MockResources.pizzas.withIndex()){
            onView(withId(R.id.pizzas_recycler_view)).perform(scrollToPosition<RecyclerView.ViewHolder>(i))
            onView(withText(pizza)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun mainActivity_clickElements() {
        for ((i, pizza) in MockResources.pizzas.withIndex()){
            onView(withId(R.id.pizzas_recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, click()))
            onView(withText(pizza)).check(matches(isDisplayed()))
            pressBack()
        }
    }

}