package com.poly.poly_sender_android.ui.auth.restore.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface RestoreWish: Wish {
    data class LoginIn(val email: String, val password: String): RestoreWish
}
