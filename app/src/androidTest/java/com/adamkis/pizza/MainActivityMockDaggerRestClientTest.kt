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
import com.adamkis.pizza.network.PIZZA_URL_BASE
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Created by adam on 2017. 12. 31..
 */
class MainActivityMockDaggerRestClientTest {


    @Suppress("unused") // actually used by Espresso
    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, false, false)
    val app by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App }

    @Before
    fun setUp() {
        val mockNetComponent =  DaggerMockNetComponent.builder()
                .mockOkHttpModule(MockOkHttpModule())
                .formatInterceptorModule(FormatInterceptorModule())
                .gsonConverterFactoryModule(GsonConverterFactoryModule())
                .loggingInterceptorModule(LoggingInterceptorModule())
                .restApiModule(RestApiModule())
                .retrofitModule(RetrofitModule(PIZZA_URL_BASE))
                .build()
        app.setNetComponent(mockNetComponent)
        activityRule.launchActivity(Intent())
    }

    @Test
    fun homeActivity_firstPhotoTitleFound() {
        onView(withText("Pukaskwa Coastal Trail Aug-Sept 2017")).check(matches(isDisplayed()))
    }

}