package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.State

data class CreationFilterState(
    val isLoading: Boolean,
    val users: List<User>,
): State
