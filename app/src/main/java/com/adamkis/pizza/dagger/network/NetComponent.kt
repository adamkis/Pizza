package com.adamkis.pizza.dagger.network

import com.adamkis.pizza.ui.activity.MainActivity
import com.adamkis.pizza.ui.fragment.PizzasFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by adam on 2018. 01. 05..
 */
@Singleton
@Component(modules = arrayOf(
        OkHttpModule::class,
        FormatInterceptorModule::class,
        ApiKeyInterceptorModule::class,
        GsonConverterFactoryModule::class,
        LoggingInterceptorModule::class,
        RestApiModule::class,
        RetrofitModule::class))
interface NetComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(pizzasFragment: PizzasFragment)
}
