package com.poly.poly_sender_android.ui.auth.login.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Effect

sealed interface LoginEffect: Effect {
    object LoginInProcess: LoginEffect
    data class LoginSuccess(val INFO: String): LoginEffect
    data class LoginFailure(val errorMessage: String): LoginEffect
}
