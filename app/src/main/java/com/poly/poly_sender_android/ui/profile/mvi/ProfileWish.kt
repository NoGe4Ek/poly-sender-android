package com.poly.poly_sender_android.ui.profile.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface ProfileWish: Wish {
    data class LoginIn(val email: String, val password: String): ProfileWish
}
