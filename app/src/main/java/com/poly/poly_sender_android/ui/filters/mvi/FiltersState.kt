package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.data.models.domainModel.Filter
import com.poly.poly_sender_android.mvi.State
import com.poly.poly_sender_android.ui.filters.FiltersSearchParam

data class FiltersState(
    val isLoading: Boolean,
    val filters: List<Filter>,
    val filtersSearchParam: FiltersSearchParam
) : State
