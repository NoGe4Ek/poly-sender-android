package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.mvi.Actor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CreationAttributeActor : Actor<CreationAttributeState, CreationAttributeWish, CreationAttributeEffect>() {

    override suspend fun effect(
        state: CreationAttributeState,
        wish: CreationAttributeWish
    ): Flow<CreationAttributeEffect?> = flow {
        when (wish) {
            is CreationAttributeWish.RefreshFromNetwork -> {
                emit(CreationAttributeEffect.RefreshInProcess(true))
                try {
                    val users = mainRepository.cacheAndGetUsers()
                    if (users.isNotEmpty())
                        emit(
                            CreationAttributeEffect.RefreshSuccess(
                                false,
                                users,
                            )
                        )
                    else
                        emit(CreationAttributeEffect.RefreshFailure(false, "Users is missing"))

                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(CreationAttributeEffect.RefreshFailure(false, errorMessage))
                }
            }

            is CreationAttributeWish.SmartRefresh -> {
                emit(CreationAttributeEffect.RefreshInProcess(true))
                try {
                    val users = mainRepository.spGetUsers()
                    if (users.isNotEmpty())
                        emit(
                            CreationAttributeEffect.RefreshSuccess(
                                false,
                                users,
                            )
                        )
                    else
                        emit(CreationAttributeEffect.RefreshFailure(false, "Local users is missing"))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(CreationAttributeEffect.RefreshFailure(false, errorMessage))
                }

            }
        }
    }.flowOn(Dispatchers.IO)
}