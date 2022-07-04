package com.poly.poly_sender_android.ui.filters.creationFilter.mvi

import com.poly.poly_sender_android.data.models.domainModel.*
import com.poly.poly_sender_android.mvi.State

data class CreationFilterState(
    //CreationAttribute
    val label: String,
    val isLoading: Boolean,
    val isEdit: Boolean,
    val editableFilter: Filter?,

    //CreationAttributeParam
    val selectedName: String,
    val selectedMailingMode: String, //TODO class mailing
    val mailingModes: Set<String>,

    //CreationAttributeSelection
    val students: Set<Student>,
    val selectedStudents: Set<String>,

    //CreationAttributeSelectionAttributing
    // Search Param // TODO pack to CreationAttributeSearchParam
    //val searchParam: CreationAttributeSearchParam,
    val searchAttributes: Set<Attribute>,
    val searchSelectedAttributes: Set<Attribute>,
    val searchSelectedSection: Section?,
    val sections: Set<Section>,
): State
