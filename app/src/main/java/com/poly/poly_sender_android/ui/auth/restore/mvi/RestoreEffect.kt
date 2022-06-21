package com.poly.poly_sender_android.ui.auth.restore.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginEffect

sealed interface RestoreEffect: Effect {
    object Loading : RestoreEffect
    object Success : RestoreEffect
    data class Failure(val errorMessage: String) : RestoreEffect
}
