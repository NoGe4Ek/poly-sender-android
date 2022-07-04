package com.poly.poly_sender_android.ui.filterProfile.mvi

import com.poly.poly_sender_android.data.models.domainModel.Filter
import com.poly.poly_sender_android.mvi.Effect

sealed interface FilterProfileEffect : Effect {
    object Loading : FilterProfileEffect
    data class Success(val filter: Filter) : FilterProfileEffect
    data class Failure(val errorMessage: String) : FilterProfileEffect

    data class DeleteFilterSuccess(val filter: Filter) : FilterProfileEffect
    data class DeleteFilterFailure(val errorMessage: String) : FilterProfileEffect
}
