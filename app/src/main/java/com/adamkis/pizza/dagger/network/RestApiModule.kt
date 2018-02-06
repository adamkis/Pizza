package com.adamkis.pizza.dagger.network

import com.adamkis.pizza.network.RestApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by adam on 2018. 01. 07..
 */
@Module
class RestApiModule() {
    @Provides
    @Singleton
    fun provideRestApi(retrofit: Retrofit): RestApi {
        return retrofit.create<RestApi>(RestApi::class.java)
    }
}