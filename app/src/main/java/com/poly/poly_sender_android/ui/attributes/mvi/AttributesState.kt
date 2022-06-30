package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.mvi.State

data class AttributesState(
    val isLoading: Boolean,
    val attributes: Set<Attribute>,

    val searchSections: Set<Section>,
    val searchSelectedSection: Section?,
): State
