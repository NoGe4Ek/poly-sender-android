package com.poly.poly_sender_android.ui.profile.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.State

data class ProfileState(
    val isLoading: Boolean,
    val user: User?,
) : State