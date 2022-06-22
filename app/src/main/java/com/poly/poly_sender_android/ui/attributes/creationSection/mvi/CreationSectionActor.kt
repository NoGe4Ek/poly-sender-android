package com.poly.poly_sender_android.ui.attributes.creationSection.mvi

import com.poly.poly_sender_android.mvi.Actor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CreationSectionActor : Actor<CreationSectionState, CreationSectionWish, CreationSectionEffect>() {

    override suspend fun effect(
        state: CreationSectionState,
        wish: CreationSectionWish
    ): Flow<CreationSectionEffect?> = flow {
        when (wish) {
            is CreationSectionWish.CreateSection -> {
                try {
                    emit(CreationSectionEffect.Loading)
                    val creationSectionResponse = mainRepository.createGroupName(mainRepository.user.idStaff, wish.sectionName)
                    emit(CreationSectionEffect.Success)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(CreationSectionEffect.Failure(errorMessage))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}