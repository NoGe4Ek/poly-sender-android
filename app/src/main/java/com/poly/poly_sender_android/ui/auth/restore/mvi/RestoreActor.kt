package com.poly.poly_sender_android.ui.auth.restore.mvi

import android.content.Intent
import android.net.Uri
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.util.MessageConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RestoreActor : Actor<RestoreState, RestoreWish, RestoreEffect>() {

    override suspend fun effect(
        state: RestoreState,
        wish: RestoreWish
    ): Flow<RestoreEffect?> = flow {
        when (wish) {
            is RestoreWish.Restore -> {
                try {
                    emit(RestoreEffect.Loading)
                    val restoreResponse = mainRepository.restorePassword(wish.login) //TODO do we use this response?
                    emit(RestoreEffect.Success)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(RestoreEffect.Failure(errorMessage))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}