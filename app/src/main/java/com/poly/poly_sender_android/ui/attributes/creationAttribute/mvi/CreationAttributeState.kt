package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.State

data class CreationAttributeState(
    val isLoading: Boolean,
    val users: List<User>,
): State
