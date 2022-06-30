package com.poly.poly_sender_android.ui.auth.login.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface LoginWish: Wish {
    data class SignIn(val login: String, val password: String): LoginWish
    object TryAutoSignIn: LoginWish
}
