package com.poly.poly_sender_android.mvi

import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.data.repositories.MainRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

abstract class Actor<S: State, W: Wish, E: Effect> constructor(
    val mainRepository: MainRepository =
        EntryPointAccessors.fromApplication(App.appContext, StoreEntryPoint::class.java).mainRepository(),
) {

    abstract suspend fun effect(state: S, wish: W): Flow<E?>
    suspend operator fun invoke(state: S, wish: W) = effect(state, wish)

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface StoreEntryPoint {
        fun mainRepository(): MainRepository
    }
}