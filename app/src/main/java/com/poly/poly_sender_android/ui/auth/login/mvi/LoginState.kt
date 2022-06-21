package com.poly.poly_sender_android.ui.auth.login.mvi

import com.poly.poly_sender_android.mvi.State

data class LoginState(
    val isLoading: Boolean,
    val password: String,
): State