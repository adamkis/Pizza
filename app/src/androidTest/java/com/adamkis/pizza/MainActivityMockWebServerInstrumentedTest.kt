package com.adamkis.pizza

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.adamkis.pizza.helper.MockResponseStrings
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
                    request.path.contains("ozt3z") -> MockResponse().setResponseCode(200).setBody(MockResponseStrings.MOCK_INGREDIENTS)
                    request.path.contains("150da7") -> MockResponse().setResponseCode(200).setBody(MockResponseStrings.MOCK_DRINKS)
                    request.path.contains("dokm7") -> MockResponse().setResponseCode(200).setBody(MockResponseStrings.MOCK_PIZZAS)
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
    fun mainActivity_isDisplayed() {
        onView(withText("Margherita")).check(matches(isDisplayed()))
        onView(withText("Ricci")).check(matches(isDisplayed()))
    }

}