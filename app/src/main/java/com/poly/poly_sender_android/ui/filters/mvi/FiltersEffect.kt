package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.data.models.domainModel.Filter
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.filters.FiltersSearchParam

sealed interface FiltersEffect : Effect {
    object Loading : FiltersEffect
    data class RefreshFiltersSuccess(val filters: Set<Filter>) :
        FiltersEffect

    data class RefreshFiltersFailure(val errorMessage: String) :
        FiltersEffect
}
