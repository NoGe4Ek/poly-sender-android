package com.poly.poly_sender_android.ui.attributeProfile.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.mvi.State

data class AttributeProfileState(
    val isLoading: Boolean,
    val attribute: Attribute?
) : State