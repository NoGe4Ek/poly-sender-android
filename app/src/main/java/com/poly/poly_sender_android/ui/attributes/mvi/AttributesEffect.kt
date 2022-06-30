package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.students.mvi.StudentsEffect

sealed interface AttributesEffect : Effect {
    object Loading : AttributesEffect

    data class RefreshAttributesSuccess(val attributes: Set<Attribute>) : AttributesEffect
    data class RefreshAttributesFailure(val errorMessage: String) : AttributesEffect

    data class RefreshSectionsSuccess(val searchSections: Set<Section>) : AttributesEffect
    data class RefreshSectionsFailure(val errorMessage: String) : AttributesEffect

    data class RefreshSelectedSectionSuccess(val searchSelectedSection: Section?) : AttributesEffect
}
