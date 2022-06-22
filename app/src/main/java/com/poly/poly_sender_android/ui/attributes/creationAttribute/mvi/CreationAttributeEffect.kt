package com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Effect

sealed interface CreationAttributeEffect : Effect {
    object Loading : CreationAttributeEffect

    //Success
    data class RefreshStudentsSuccess(val students: List<Student>) : CreationAttributeEffect
    data class RefreshSearchingAttributesBySelectedSectionSuccess(val attributes: List<Attribute>) :
        CreationAttributeEffect

    object CreateAttributeSuccess : CreationAttributeEffect
    object ClearSearchParamSuccess :
        CreationAttributeEffect

    //Failure
    data class RefreshStudentsFailure(val errorMessage: String) : CreationAttributeEffect
    data class RefreshSearchingAttributesBySelectedSectionFailure(val errorMessage: String) :
        CreationAttributeEffect

    data class CreateAttributeFailure(val errorMessage: String) : CreationAttributeEffect
    data class ClearSearchParamFailure(val errorMessage: String) : CreationAttributeEffect

    //Local Storage (only success)
    data class UpdateSharedStorageByParamSuccess(
        val selectedName: String,
        val selectedSection: String
    ) : CreationAttributeEffect

    data class UpdateSharedStorageBySelectionSuccess(
        val students: List<Student>,
        val selectedStudents: List<Student>
    ) : CreationAttributeEffect

    data class UpdateSharedStorageBySelectionAttributingSuccess(
        val searchAttributes: List<Attribute>,
        val searchSelectedAttributes: List<Attribute>,
        val searchSelectedSearchSection: String
    ) : CreationAttributeEffect
}