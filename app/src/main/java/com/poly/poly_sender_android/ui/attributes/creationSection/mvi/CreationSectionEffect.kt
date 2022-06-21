package com.poly.poly_sender_android.ui.attributes.creationSection.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.attributes.SearchParam
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesEffect
import com.poly.poly_sender_android.ui.attributes.mvi.CreationAttributeEffect

sealed interface CreationSectionEffect: Effect {
    object Loading : CreationSectionEffect
    object Success : CreationSectionEffect
    data class Failure(val errorMessage: String) : CreationSectionEffect
}
