package com.poly.poly_sender_android.ui.filterProfile.mvi

import com.poly.poly_sender_android.data.models.domainModel.Filter
import com.poly.poly_sender_android.mvi.State

data class FilterProfileState(
    val isLoading: Boolean,
    val filter: Filter?
) : State