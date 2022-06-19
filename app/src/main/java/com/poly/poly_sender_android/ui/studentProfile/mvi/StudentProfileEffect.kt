package com.poly.poly_sender_android.ui.studentProfile.mvi

import com.poly.poly_sender_android.mvi.Effect

sealed interface StudentProfileEffect: Effect {
    object LoginInProcess: StudentProfileEffect
    data class LoginSuccess(val INFO: String): StudentProfileEffect
    data class LoginFailure(val errorMessage: String): StudentProfileEffect
}
