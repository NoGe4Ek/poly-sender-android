package com.poly.poly_sender_android.ui.filters.creationFilter.mvi

import com.poly.poly_sender_android.data.models.domainModel.*
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeEffect

sealed interface CreationFilterEffect: Effect {
    object Loading : CreationFilterEffect

    //Success
    data class RefreshStudentsSuccess(val students: Set<Student>) : CreationFilterEffect
    data class RefreshSearchingAttributesBySelectedSectionSuccess(val attributes: Set<Attribute>) :
        CreationFilterEffect
    data class RefreshSectionsSuccess(val searchSections: Set<Section>) : CreationFilterEffect

    object CreateFilterSuccess : CreationFilterEffect
    object UpdateFilterSuccess : CreationFilterEffect
    object ClearSearchParamSuccess :
        CreationFilterEffect

    data class SelectStudentSuccess(val student: String) : CreationFilterEffect
    data class SelectStudentsSuccess(val students: Set<String>) : CreationFilterEffect
    data class DismissStudentSuccess(val student: String) : CreationFilterEffect
    data class DismissStudentsSuccess(val students: Set<String>) : CreationFilterEffect
    data class SelectAttributeSuccess(val attribute: Attribute) : CreationFilterEffect
    data class DismissAttributeSuccess(val attribute: Attribute) : CreationFilterEffect
    data class RefreshSelectedSectionSuccess(val searchSelectedSection: Section?) :
        CreationFilterEffect

    //Failure
    data class RefreshStudentsFailure(val errorMessage: String) : CreationFilterEffect
    data class RefreshSearchingAttributesBySelectedSectionFailure(val errorMessage: String) :
        CreationFilterEffect

    data class CreateFilterFailure(val errorMessage: String) : CreationFilterEffect
    data class UpdateFilterFailure(val errorMessage: String) : CreationFilterEffect
    data class ClearSearchParamFailure(val errorMessage: String) : CreationFilterEffect
    data class RefreshSectionsFailure(val errorMessage: String) : CreationFilterEffect

    //Local Storage (only success)
    data class UpdateSharedStorageByParamSuccess(
        val selectedName: String,
        val selectedMailingMode: String
    ) : CreationFilterEffect

    data class UpdateSharedStorageBySelectionSuccess(
        val students: Set<Student>,
        val selectedStudents: Set<String>
    ) : CreationFilterEffect

    data class UpdateSharedStorageBySelectionAttributingSuccess(
        val searchAttributes: Set<Attribute>,
        val searchSelectedAttributes: Set<Attribute>,
        val searchSelectedSearchSection: Section?
    ) : CreationFilterEffect

    object ClearSharedStorageSuccess : CreationFilterEffect
    data class SetSharedStorageSuccess(val filter: Filter, val students: Set<Student>) :
        CreationFilterEffect
}
