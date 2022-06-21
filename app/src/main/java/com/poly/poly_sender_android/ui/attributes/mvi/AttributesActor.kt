package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.mvi.Actor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AttributesActor : Actor<AttributesState, AttributesWish, AttributesEffect>() {

    override suspend fun effect(
        state: AttributesState,
        wish: AttributesWish
    ): Flow<AttributesEffect?> = flow {
        when (wish) {
            is AttributesWish.Refresh -> {
                try {
                    emit(AttributesEffect.Loading)
                    val attributes = mainRepository.getDataAttributesCurrentStaff(mainRepository.user.idStaff)
                    //TODO impl searchParam filtering
                    emit(AttributesEffect.Success(attributes, wish.searchParam))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(AttributesEffect.Failure(errorMessage))
                }
            }


        }
    }.flowOn(Dispatchers.IO)
}