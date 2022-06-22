package com.poly.poly_sender_android.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.poly.poly_sender_android.data.network.ApiRetrofit
import com.poly.poly_sender_android.data.network.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.API_ROOT_BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit.Builder): ApiRetrofit {
        return retrofit
            .build()
            .create(ApiRetrofit::class.java)
    }
}