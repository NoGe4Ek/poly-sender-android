package com.poly.poly_sender_android.ui.settings.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface SettingsWish: Wish {
    data class LoginIn(val email: String, val password: String): SettingsWish
}
