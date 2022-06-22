package com.poly.poly_sender_android.ui.filters.creationFilter.mvi

import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterEffect
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterState
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterWish
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CreationFilterActor : Actor<CreationFilterState, CreationFilterWish, CreationFilterEffect>() {

    override suspend fun effect(
        state: CreationFilterState,
        wish: CreationFilterWish
    ): Flow<CreationFilterEffect?> = flow {
        when (wish) {
            is CreationFilterWish.RefreshFromNetwork -> {
                emit(CreationFilterEffect.RefreshInProcess(true))

            }
        }
    }.flowOn(Dispatchers.IO)
}