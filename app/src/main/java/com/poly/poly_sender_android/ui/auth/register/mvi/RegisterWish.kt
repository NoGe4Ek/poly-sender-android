package com.poly.poly_sender_android.ui.auth.register.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface RegisterWish : Wish {
    data class GetAccess(
        val firstName: String,
        val lastName: String,
        val patronymic: String,
        val email: String,
        val department: String,
        val highSchool: String,
    ) : RegisterWish
}
