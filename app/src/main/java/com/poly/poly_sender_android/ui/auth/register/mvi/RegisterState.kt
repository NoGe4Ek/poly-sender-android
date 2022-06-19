package com.poly.poly_sender_android.ui.auth.register.mvi

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.State

data class RegisterState(
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val email: String,
    val institute: String,
    val highSchool: String,
): State