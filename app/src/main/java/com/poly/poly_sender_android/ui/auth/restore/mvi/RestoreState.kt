package com.poly.poly_sender_android.ui.auth.restore.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.State

data class RestoreState(
    val email: String,
    val password: String,
): State