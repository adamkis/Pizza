package com.adamkis.pizza.dagger

import com.adamkis.pizza.dagger.network.*
import com.adamkis.pizza.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by adam on 2018. 01. 05..
 */
@Singleton
@Component(modules = arrayOf(
        MockOkHttpModule::class,
        FormatInterceptorModule::class,
        GsonConverterFactoryModule::class,
        LoggingInterceptorModule::class,
        RestApiModule::class,
        RetrofitModule::class))
interface MockNetComponent : NetComponent {
    override fun inject(activity: MainActivity)
}