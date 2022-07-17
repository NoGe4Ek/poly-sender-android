package com.poly.poly_sender_android.ui.settings.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface SettingsWish: Wish {
    object Logout: SettingsWish
}
