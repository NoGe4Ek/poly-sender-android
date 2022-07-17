package com.poly.poly_sender_android.ui.settings.mvi

import com.poly.poly_sender_android.mvi.Effect

sealed interface SettingsEffect: Effect {
    object LogoutSuccess: SettingsEffect
    data class LogoutFailure(val errorMessage: String): SettingsEffect
}
