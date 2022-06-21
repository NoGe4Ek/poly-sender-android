package com.poly.poly_sender_android.ui.auth.register.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginEffect

sealed interface RegisterEffect: Effect {
    object Loading : RegisterEffect
    object Success : RegisterEffect
    data class Failure(val errorMessage: String) : RegisterEffect
}
