package com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Wish
import com.poly.poly_sender_android.ui.attributes.creationSection.mvi.CreationSectionWish
import com.poly.poly_sender_android.ui.students.mvi.StudentsWish

sealed interface CreationAttributeWish : Wish {
    data class RefreshStudents(val searchSelectedAttributes: Set<Attribute>, val query: String) :
        CreationAttributeWish

    data class RefreshSearchingAttributesBySelectedSection(val selectedSearchSection: Section?) :
        CreationAttributeWish

    object RefreshSections : CreationAttributeWish
    data class RefreshSelectedSection(val section: Section?) : CreationAttributeWish

    data class CreateAttribute(
        val attributeName: String,
        val section: String,
        val students: Set<String>
    ) : CreationAttributeWish

    data class UpdateAttribute(
        val attributeName: String,
        val section: String,
        val students: Set<String>
    ) : CreationAttributeWish

    object ClearSearchParam : CreationAttributeWish // delete selected attributes + reload all students(not selected)

    data class SelectStudent(val student: String): CreationAttributeWish
    data class SelectStudents(val students: Set<String>): CreationAttributeWish
    data class DismissStudent(val student: String): CreationAttributeWish
    data class DismissStudents(val students: Set<String>): CreationAttributeWish

    data class SelectAttribute(val attribute: Attribute): CreationAttributeWish
    data class DismissAttribute(val attribute: Attribute): CreationAttributeWish

    //Local Storage for SharedViewModel restore states
    data class UpdateSharedStorageByParam(val selectedName: String, val selectedSection: String) :
        CreationAttributeWish

    data class UpdateSharedStorageBySelection(
        val students: Set<Student>,
        val selectedStudents: Set<String>
    ) : CreationAttributeWish

    data class UpdateSharedStorageBySelectionAttributing(
        val searchAttributes: Set<Attribute>,
        val searchSelectedAttributes: Set<Attribute>,
        val searchSelectedSearchSection: Section?
    ) : CreationAttributeWish

    object ClearSharedStorage : CreationAttributeWish
    data class SetSharedStorage(val attribute: Attribute) : CreationAttributeWish
}