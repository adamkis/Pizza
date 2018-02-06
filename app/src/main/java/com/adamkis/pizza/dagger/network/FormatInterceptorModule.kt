package com.adamkis.pizza.dagger.network

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by adam on 2018. 01. 07..
 */
@Module
class FormatInterceptorModule() {
    @Provides
    @Singleton
    @Named("format")
    fun provideFormatInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val url = request.url().newBuilder()
                    .addQueryParameter("format", "json")
                    .addQueryParameter("nojsoncallback", "1")
                    .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }
}