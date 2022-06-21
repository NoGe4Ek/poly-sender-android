package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.attributes.SearchParam

sealed interface AttributesEffect : Effect {
    object Loading : AttributesEffect
    data class Success(val attributes: List<Attribute>, val searchParam: SearchParam) : AttributesEffect
    data class Failure(val errorMessage: String) : AttributesEffect
}
