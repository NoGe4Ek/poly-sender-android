package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.mvi.State
import com.poly.poly_sender_android.ui.attributes.AttributesSearchParam

data class AttributesState(
    val isLoading: Boolean,
    val attributes: List<Attribute>,
    val attributesSearchParam: AttributesSearchParam
): State
