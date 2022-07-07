package com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.State

data class CreationAttributeState(
    //CreationAttribute
    val label: String,
    val isLoading: Boolean,
    val isEdit: Boolean,
    val editableAttribute: Attribute?,

    //CreationAttributeParam
    val selectedName: String,
    val selectedSection: String,
    val sections: Set<Section>,

    //CreationAttributeSelection
    val students: Set<Student>,
    val selectedStudents: Set<String>,

    //CreationAttributeSelectionAttributing
    // Search Param // TODO pack to CreationAttributeSearchParam
    //val searchParam: CreationAttributeSearchParam,
    val searchAttributes: Set<Attribute>,
    val searchSelectedAttributes: Set<Attribute>,
    val searchSelectedSection: Section?,
): State
