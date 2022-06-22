package com.poly.poly_sender_android.ui.attributes.creationSection.mvi

import com.poly.poly_sender_android.mvi.Effect

sealed interface CreationSectionEffect: Effect {
    object Loading : CreationSectionEffect
    object Success : CreationSectionEffect
    data class Failure(val errorMessage: String) : CreationSectionEffect
}
