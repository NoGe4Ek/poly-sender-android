package com.poly.poly_sender_android.ui.studentProfile.mvi

import com.poly.poly_sender_android.mvi.Effect

sealed interface StudentProfileEffect : Effect {
    object Loading : StudentProfileEffect
    object Success : StudentProfileEffect
    data class Failure(val errorMessage: String) : StudentProfileEffect
}
