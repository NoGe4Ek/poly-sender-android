package com.poly.poly_sender_android.ui.auth.restore.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Effect

sealed interface RestoreEffect: Effect {
    data class GetUserDetailsSuccess(val user: User, val friends: List<User>): RestoreEffect
    data class GetUserDetailsFailure(val errorMessage: String): RestoreEffect

    object ExternalSuccess: RestoreEffect
    data class ExternalEmailFailure(val errorMessage: String): RestoreEffect
    data class ExternalCallFailure(val errorMessage: String): RestoreEffect
    data class ExternalMapFailure(val errorMessage: String): RestoreEffect
}
