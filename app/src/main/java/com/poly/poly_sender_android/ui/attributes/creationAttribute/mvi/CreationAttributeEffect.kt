package com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesEffect
import com.poly.poly_sender_android.ui.students.mvi.StudentsEffect

sealed interface CreationAttributeEffect : Effect {
    object Loading : CreationAttributeEffect

    //Success
    data class RefreshStudentsSuccess(val students: Set<Student>) : CreationAttributeEffect
    data class RefreshSearchingAttributesBySelectedSectionSuccess(val attributes: Set<Attribute>) :
        CreationAttributeEffect
    data class RefreshSectionsSuccess(val searchSections: Set<Section>) : CreationAttributeEffect

    object CreateAttributeSuccess : CreationAttributeEffect
    object UpdateAttributeSuccess : CreationAttributeEffect
    object ClearSearchParamSuccess :
        CreationAttributeEffect

    data class SelectStudentSuccess(val student: String) : CreationAttributeEffect
    data class SelectStudentsSuccess(val students: Set<String>) : CreationAttributeEffect
    data class DismissStudentSuccess(val student: String) : CreationAttributeEffect
    data class DismissStudentsSuccess(val students: Set<String>) : CreationAttributeEffect
    data class SelectAttributeSuccess(val attribute: Attribute) : CreationAttributeEffect
    data class DismissAttributeSuccess(val attribute: Attribute) : CreationAttributeEffect
    data class RefreshSelectedSectionSuccess(val searchSelectedSection: Section?) : CreationAttributeEffect

    //Failure
    data class RefreshStudentsFailure(val errorMessage: String) : CreationAttributeEffect
    data class RefreshSearchingAttributesBySelectedSectionFailure(val errorMessage: String) :
        CreationAttributeEffect

    data class CreateAttributeFailure(val errorMessage: String) : CreationAttributeEffect
    data class UpdateAttributeFailure(val errorMessage: String) : CreationAttributeEffect
    data class ClearSearchParamFailure(val errorMessage: String) : CreationAttributeEffect
    data class RefreshSectionsFailure(val errorMessage: String) : CreationAttributeEffect

    //Local Storage (only success)
    data class UpdateSharedStorageByParamSuccess(
        val selectedName: String,
        val selectedSection: String
    ) : CreationAttributeEffect

    data class UpdateSharedStorageBySelectionSuccess(
        val students: Set<Student>,
        val selectedStudents: Set<String>
    ) : CreationAttributeEffect

    data class UpdateSharedStorageBySelectionAttributingSuccess(
        val searchAttributes: Set<Attribute>,
        val searchSelectedAttributes: Set<Attribute>,
        val searchSelectedSearchSection: Section?
    ) : CreationAttributeEffect

    object ClearSharedStorageSuccess : CreationAttributeEffect
    data class SetSharedStorageSuccess(val attribute: Attribute, val students: Set<Student>) : CreationAttributeEffect
}