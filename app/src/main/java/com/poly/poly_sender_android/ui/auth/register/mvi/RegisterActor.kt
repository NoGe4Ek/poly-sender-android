package com.poly.poly_sender_android.ui.auth.register.mvi

import com.poly.poly_sender_android.data.models.domainModel.GetAccessResponse
import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.util.ErrorConstants.STATUS_FALSE
import com.poly.poly_sender_android.util.ErrorConstants.UNKNOWN_EXCEPTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterActor : Actor<RegisterState, RegisterWish, RegisterEffect>() {

    override suspend fun effect(
        state: RegisterState,
        wish: RegisterWish
    ): Flow<RegisterEffect?> = flow {
        when (wish) {
            is RegisterWish.GetAccess -> {
                try {
                    emit(RegisterEffect.Loading)
                    var getAccessResponse: GetAccessResponse
                    with(wish) {
                        getAccessResponse = mainRepository.getAccess(
                            firstName, lastName, patronymic, email, department, highSchool
                        )
                    }
                    if (getAccessResponse.status) {
                        emit(RegisterEffect.Success)
                    } else {
                        emit(RegisterEffect.Failure(STATUS_FALSE))
                    }

                } catch (e: Exception) {
                    val errorMessage = e.message ?: UNKNOWN_EXCEPTION
                    emit(RegisterEffect.Failure(errorMessage))
                }
            }

        }
    }.flowOn(Dispatchers.IO)
}