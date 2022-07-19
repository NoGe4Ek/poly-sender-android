package com.poly.poly_sender_android.ui.settings.mvi

import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.ui.profile.mvi.ProfileEffect
import com.poly.poly_sender_android.util.MessageConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SettingsActor: Actor<SettingsState, SettingsWish, SettingsEffect>() {

    override suspend fun effect(
        state: SettingsState,
        wish: SettingsWish
    ): Flow<SettingsEffect?> = flow {
        when (wish) {
            is SettingsWish.Logout -> {
                try {
                    mainRepository.nukeTable()
                    mainRepository.clearAuthToken()
                    emit(SettingsEffect.LogoutSuccess)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(SettingsEffect.LogoutFailure(errorMessage))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}