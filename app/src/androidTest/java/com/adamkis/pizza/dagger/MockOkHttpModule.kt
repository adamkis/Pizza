package com.adamkis.pizza.dagger

import com.adamkis.pizza.helper.MockResources
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class MockOkHttpModule() {
    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val responseInterceptor = object : Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                return Response.Builder()
                        .code(200)
                        .message(MockResources.MOCK_PIZZAS)
                        .request(chain.request())
                        .protocol(Protocol.HTTP_1_0)
                        .body(ResponseBody.create(MediaType.parse("application/json"), MockResources.MOCK_PIZZAS.toByteArray()))
                        .addHeader("content-type", "application/json")
                        .build()
            }
        }
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(responseInterceptor)
                .build()
    }
}