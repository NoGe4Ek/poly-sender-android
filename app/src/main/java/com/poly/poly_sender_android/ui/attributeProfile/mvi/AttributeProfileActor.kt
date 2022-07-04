package com.poly.poly_sender_android.ui.attributeProfile.mvi

import com.poly.poly_sender_android.mvi.Actor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AttributeProfileActor :
    Actor<AttributeProfileState, AttributeProfileWish, AttributeProfileEffect>() {

    override suspend fun effect(
        state: AttributeProfileState,
        wish: AttributeProfileWish
    ): Flow<AttributeProfileEffect?> = flow {
        when (wish) {
            is AttributeProfileWish.SetAttribute -> {
                try {
                    emit(AttributeProfileEffect.Success(wish.attribute))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(AttributeProfileEffect.Failure(errorMessage))
                }
            }
            is AttributeProfileWish.DeleteAttribute -> {
                try {
                    mainRepository.deleteAttribute(wish.attribute)
                    emit(AttributeProfileEffect.DeleteAttributeSuccess(wish.attribute))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(AttributeProfileEffect.DeleteAttributeFailure(errorMessage))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}