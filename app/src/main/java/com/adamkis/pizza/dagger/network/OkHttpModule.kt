package com.adamkis.pizza.dagger.network

import com.adamkis.pizza.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class OkHttpModule() {
    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        var builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG){
            builder = builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }
}