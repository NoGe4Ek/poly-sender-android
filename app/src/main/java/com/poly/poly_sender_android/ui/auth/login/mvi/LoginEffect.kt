package com.poly.poly_sender_android.ui.auth.login.mvi

import com.poly.poly_sender_android.data.models.domainModel.Role
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Effect

sealed interface LoginEffect : Effect {
    object Loading : LoginEffect
    data class Success(val user: User) : LoginEffect
    data class Failure(val errorMessage: String) : LoginEffect
}
