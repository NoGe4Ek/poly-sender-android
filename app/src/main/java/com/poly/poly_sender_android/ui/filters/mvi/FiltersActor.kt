package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.mvi.Actor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FiltersActor : Actor<FiltersState, FiltersWish, FiltersEffect>() {

    override suspend fun effect(
        state: FiltersState,
        wish: FiltersWish
    ): Flow<FiltersEffect?> = flow {
        when (wish) {
            is FiltersWish.Refresh -> {
                emit(FiltersEffect.Loading)
                try {
                    val filters = mainRepository.getFilters(mainRepository.user.idStaff)
                    emit(FiltersEffect.Success(filters, wish.filtersSearchParam))

                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(FiltersEffect.Failure(errorMessage))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}