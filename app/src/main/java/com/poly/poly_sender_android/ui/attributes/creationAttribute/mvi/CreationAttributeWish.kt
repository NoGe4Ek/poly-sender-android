package com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Wish
import com.poly.poly_sender_android.ui.attributes.creationSection.mvi.CreationSectionWish

sealed interface CreationAttributeWish : Wish {
    data class RefreshStudents(val searchSelectedAttributes: List<Attribute>) :
        CreationAttributeWish

    data class RefreshSearchingAttributesBySelectedSection(val selectedSearchSection: String) :
        CreationAttributeWish

    data class CreateAttribute(
        val attributeName: String,
        val section: String,
        val students: List<Student>
    ) : CreationAttributeWish

    object ClearSearchParam : CreationAttributeWish // delete selected attributes + reload all students(not selected)

    //Local Storage for SharedViewModel restore states
    data class UpdateSharedStorageByParam(val selectedName: String, val selectedSection: String) :
        CreationAttributeWish

    data class UpdateSharedStorageBySelection(
        val students: List<Student>,
        val selectedStudents: List<Student>
    ) : CreationAttributeWish

    data class UpdateSharedStorageBySelectionAttributing(
        val searchAttributes: List<Attribute>,
        val searchSelectedAttributes: List<Attribute>,
        val searchSelectedSearchSection: String
    ) : CreationAttributeWish
}