package com.poly.poly_sender_android.ui.profile.mvi

import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.util.ErrorConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileActor : Actor<ProfileState, ProfileWish, ProfileEffect>() {

    override suspend fun effect(
        state: ProfileState,
        wish: ProfileWish
    ): Flow<ProfileEffect?> = flow {
        when (wish) {
            is ProfileWish.FetchUser -> {
                try {
                    emit(ProfileEffect.Loading)
                    val user = mainRepository.user
                    emit(ProfileEffect.FetchUserSuccess(user))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: ErrorConstants.UNKNOWN_EXCEPTION
                    emit(ProfileEffect.FetchUserFailure(errorMessage))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}