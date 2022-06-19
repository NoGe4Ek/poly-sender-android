package com.poly.poly_sender_android.ui.profile.mvi

import com.poly.poly_sender_android.mvi.Effect

sealed interface ProfileEffect: Effect {
    object LoginInProcess: ProfileEffect
    data class LoginSuccess(val INFO: String): ProfileEffect
    data class LoginFailure(val errorMessage: String): ProfileEffect
}
