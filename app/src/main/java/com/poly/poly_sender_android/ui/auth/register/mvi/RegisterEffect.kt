package com.poly.poly_sender_android.ui.auth.register.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Effect

sealed interface RegisterEffect: Effect {
    data class GetUserDetailsSuccess(val user: User, val friends: List<User>): RegisterEffect
    data class GetUserDetailsFailure(val errorMessage: String): RegisterEffect

    object ExternalSuccess: RegisterEffect
    data class ExternalEmailFailure(val errorMessage: String): RegisterEffect
    data class ExternalCallFailure(val errorMessage: String): RegisterEffect
    data class ExternalMapFailure(val errorMessage: String): RegisterEffect
}
