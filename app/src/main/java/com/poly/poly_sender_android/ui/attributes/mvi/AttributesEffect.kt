package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Effect

sealed interface AttributesEffect: Effect {
    data class RefreshInProcess(val isLoading: Boolean = true): CreationAttributeEffect
    data class RefreshSuccess(val isLoading: Boolean = false, val users: List<User>): CreationAttributeEffect
    data class RefreshFailure(val isLoading: Boolean = false, val errorMessage: String):
        CreationAttributeEffect
}
