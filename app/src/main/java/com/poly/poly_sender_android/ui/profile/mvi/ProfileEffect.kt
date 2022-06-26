package com.poly.poly_sender_android.ui.profile.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Effect

sealed interface ProfileEffect: Effect {
    object Loading: ProfileEffect
    data class FetchUserSuccess(val user: User): ProfileEffect
    data class FetchUserFailure(val errorMessage: String): ProfileEffect
}
