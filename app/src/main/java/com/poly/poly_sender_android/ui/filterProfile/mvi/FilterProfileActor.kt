package com.poly.poly_sender_android.ui.filterProfile.mvi

import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.util.MessageConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FilterProfileActor :
    Actor<FilterProfileState, FilterProfileWish, FilterProfileEffect>() {

    override suspend fun effect(
        state: FilterProfileState,
        wish: FilterProfileWish
    ): Flow<FilterProfileEffect?> = flow {
        when (wish) {
            is FilterProfileWish.SetFilter -> {
                try {
                    emit(FilterProfileEffect.Success(wish.filter))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(FilterProfileEffect.Failure(errorMessage))
                }
            }
            is FilterProfileWish.DeleteFilter -> {
                try {
                    mainRepository.deleteFilter(wish.filter)
                    emit(FilterProfileEffect.DeleteFilterSuccess(wish.filter))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(FilterProfileEffect.DeleteFilterFailure(errorMessage))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}