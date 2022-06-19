package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.mvi.Actor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FiltersActor : Actor<CreationFilterState, CreationFilterWish, CreationFilterEffect>() {

    override suspend fun effect(
        state: CreationFilterState,
        wish: CreationFilterWish
    ): Flow<CreationFilterEffect?> = flow {
        when (wish) {
            is CreationFilterWish.RefreshFromNetwork -> {
                emit(CreationFilterEffect.RefreshInProcess(true))
                try {
                    val users = mainRepository.cacheAndGetUsers()
                    if (users.isNotEmpty())
                        emit(
                            CreationFilterEffect.RefreshSuccess(
                                false,
                                users,
                            )
                        )
                    else
                        emit(CreationFilterEffect.RefreshFailure(false, "Users is missing"))

                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(CreationFilterEffect.RefreshFailure(false, errorMessage))
                }
            }

            is CreationFilterWish.SmartRefresh -> {
                emit(CreationFilterEffect.RefreshInProcess(true))
                try {
                    val users = mainRepository.spGetUsers()
                    if (users.isNotEmpty())
                        emit(
                            CreationFilterEffect.RefreshSuccess(
                                false,
                                users,
                            )
                        )
                    else
                        emit(CreationFilterEffect.RefreshFailure(false, "Local users is missing"))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(CreationFilterEffect.RefreshFailure(false, errorMessage))
                }

            }
        }
    }.flowOn(Dispatchers.IO)
}