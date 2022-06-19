package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Effect

sealed interface FiltersEffect: Effect {
    data class RefreshInProcess(val isLoading: Boolean = true): CreationFilterEffect
    data class RefreshSuccess(val isLoading: Boolean = false, val users: List<User>): CreationFilterEffect
    data class RefreshFailure(val isLoading: Boolean = false, val errorMessage: String):
        CreationFilterEffect
}
