package com.anteeone.coverit.di.modules

import com.anteeone.coverit.data.network.ApiInterface
import com.anteeone.coverit.data.network.Constants
import com.google.protobuf.Api
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {

    private val BASE_URL = "https://ws.audioscrobbler.com/2.0/"

    @Singleton
    @Provides
    fun provideOkHttpClient(apiKeyInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30,TimeUnit.SECONDS)
            .addInterceptor(apiKeyInterceptor)
            .build()

    @Singleton
    @Provides
    fun profiteRetrofit(client: OkHttpClient):Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun providesApiKeyInterceptor():Interceptor = Interceptor{chain ->
        val original = chain.request()
        original.url().newBuilder()
            .addQueryParameter("api_key",Constants.API_KEY)
            .build()
            .let { chain.proceed(
                original.newBuilder().url(it).build()
            ) }
    }

    @Provides
    @Singleton
    fun providesApiInterface(retrofit: Retrofit):ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

}