package com.adamkis.pizza.dagger.network

import com.adamkis.pizza.ui.activity.MainActivity
import com.adamkis.pizza.ui.fragment.CartFragment
import com.adamkis.pizza.ui.fragment.DrinksFragment
import com.adamkis.pizza.ui.fragment.PizzasFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        OkHttpModule::class,
        GsonConverterFactoryModule::class,
        LoggingInterceptorModule::class,
        RestApiModule::class,
        RetrofitModule::class))
interface NetComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(pizzasFragment: PizzasFragment)
    fun inject(drinksFragment: DrinksFragment)
    fun inject(cartFragment: CartFragment)
}
