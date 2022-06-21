package com.poly.poly_sender_android.ui.studentProfile.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.attributes.SearchParam
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesEffect

sealed interface StudentProfileEffect : Effect {
    object Loading : StudentProfileEffect
    object Success : StudentProfileEffect
    data class Failure(val errorMessage: String) : StudentProfileEffect
}
