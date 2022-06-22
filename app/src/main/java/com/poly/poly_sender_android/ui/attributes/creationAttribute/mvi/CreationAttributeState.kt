package com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.State

data class CreationAttributeState(
    //CreationAttribute
    val isLoading: Boolean,

    //CreationAttributeParam
    val selectedName: String,
    val selectedSection: String, //TODO class sections

    //CreationAttributeSelection
    val students: List<Student>,
    val selectedStudents: List<Student>,

    //CreationAttributeSelectionAttributing
    // Search Param // TODO pack to CreationAttributeSearchParam
    //val searchParam: CreationAttributeSearchParam,
    val searchAttributes: List<Attribute>,
    val searchSelectedAttributes: List<Attribute>,
    val searchSelectedSearchSection: String, //TODO class sections
): State
