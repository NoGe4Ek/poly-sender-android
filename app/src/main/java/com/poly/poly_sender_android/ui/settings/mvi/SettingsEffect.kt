package com.poly.poly_sender_android.ui.settings.mvi

import com.poly.poly_sender_android.mvi.Effect

sealed interface SettingsEffect: Effect {
    object LoginInProcess: SettingsEffect
    data class LoginSuccess(val INFO: String): SettingsEffect
    data class LoginFailure(val errorMessage: String): SettingsEffect
}
