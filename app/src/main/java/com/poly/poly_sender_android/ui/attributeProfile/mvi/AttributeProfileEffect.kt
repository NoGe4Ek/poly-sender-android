package com.poly.poly_sender_android.ui.attributeProfile.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.mvi.Effect

sealed interface AttributeProfileEffect : Effect {
    object Loading : AttributeProfileEffect
    data class Success(val attribute: Attribute) : AttributeProfileEffect
    data class Failure(val errorMessage: String) : AttributeProfileEffect

    data class DeleteAttributeSuccess(val attribute: Attribute) : AttributeProfileEffect
    data class DeleteAttributeFailure(val errorMessage: String) : AttributeProfileEffect
}
