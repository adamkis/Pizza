package com.adamkis.pizza.dagger

import com.adamkis.pizza.dagger.network.*
import com.adamkis.pizza.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        MockOkHttpModule::class,
        GsonConverterFactoryModule::class,
        LoggingInterceptorModule::class,
        RestApiModule::class,
        RetrofitModule::class))
interface MockNetComponent : NetComponent {
    override fun inject(activity: MainActivity)
}