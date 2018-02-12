package com.adamkis.pizza

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import com.adamkis.pizza.ui.activity.MainActivity

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import com.adamkis.pizza.dagger.*
import com.adamkis.pizza.dagger.network.*
import com.adamkis.pizza.network.RestApi.Companion.PIZZA_URL_BASE
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityMockDaggerRestClientTest {


    @Suppress("unused") // actually used by Espresso
    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, false, false)
    val app by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App }

    @Before
    fun setUp() {
        val mockNetComponent =  DaggerMockNetComponent.builder()
                .mockOkHttpModule(MockOkHttpModule())
                .gsonConverterFactoryModule(GsonConverterFactoryModule())
                .loggingInterceptorModule(LoggingInterceptorModule())
                .restApiModule(RestApiModule())
                .retrofitModule(RetrofitModule(PIZZA_URL_BASE))
                .build()
        app.setNetComponent(mockNetComponent)
        activityRule.launchActivity(Intent())
    }


    // TODO - as of now not working
//    @Test
//    fun mainActivity_Shown() {
//        onView(withText("Margherita")).check(matches(isDisplayed()))
//    }

}