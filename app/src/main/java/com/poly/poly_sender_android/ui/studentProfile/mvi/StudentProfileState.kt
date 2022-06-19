package com.poly.poly_sender_android.ui.studentProfile.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.State

data class StudentProfileState(
    val email: String,
    val password: String,
): State