package com.poly.poly_sender_android.di

import com.poly.poly_sender_android.data.repositories.MainRepository
import com.poly.poly_sender_android.data.repositories.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Repositories will live same as the activity that requires them
 * Bind Interface to Impl
 */

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun providesMainRepository(impl: MainRepositoryImpl): MainRepository
}