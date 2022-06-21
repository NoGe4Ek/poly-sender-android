package com.poly.poly_sender_android.ui.auth.register.mvi

import com.poly.poly_sender_android.mvi.State

data class RegisterState(
    val isLoading: Boolean,
): State