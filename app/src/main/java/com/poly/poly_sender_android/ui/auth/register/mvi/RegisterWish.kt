package com.poly.poly_sender_android.ui.auth.register.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface RegisterWish: Wish {
    data class LoginIn(val email: String, val password: String): RegisterWish
}
