package com.poly.poly_sender_android.ui.settings.mvi

import com.poly.poly_sender_android.mvi.Actor
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
            is SettingsWish.LoginIn -> {
                emit(SettingsEffect.LoginInProcess)
            }
        }
    }.flowOn(Dispatchers.IO)
}