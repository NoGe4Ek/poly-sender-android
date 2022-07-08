package com.poly.poly_sender_android.di

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StoreModule {
    @Provides
    @Singleton
    fun provideStore(
        logger: Logger
    ): Store<State, Wish, Effect, News> {
        return Store()
    }

    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return Logger()
    }
}