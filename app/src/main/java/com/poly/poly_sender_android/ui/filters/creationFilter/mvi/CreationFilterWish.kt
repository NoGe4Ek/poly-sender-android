package com.poly.poly_sender_android.ui.filters.creationFilter.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Filter
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Wish
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish

sealed interface CreationFilterWish: Wish {
    data class RefreshStudents(val searchSelectedAttributes: Set<Attribute>, val query: String) :
        CreationFilterWish

    data class RefreshSearchingAttributesBySelectedSection(val selectedSearchSection: Section?) :
        CreationFilterWish

    object RefreshSections : CreationFilterWish
    data class RefreshSelectedSection(val section: Section?) : CreationFilterWish

    data class CreateFilter(
        val filterName: String,
        val mailingMode: String,
        val students: Set<String>
    ) : CreationFilterWish

    data class UpdateFilter(
        val filterName: String,
        val mailingMode: String,
        val students: Set<String>
    ) : CreationFilterWish

    object ClearSearchParam :
        CreationFilterWish // delete selected attributes + reload all students(not selected)

    data class SelectStudent(val student: String): CreationFilterWish
    data class SelectStudents(val students: Set<String>): CreationFilterWish
    data class DismissStudent(val student: String): CreationFilterWish
    data class DismissStudents(val students: Set<String>): CreationFilterWish

    data class SelectAttribute(val attribute: Attribute): CreationFilterWish
    data class DismissAttribute(val attribute: Attribute): CreationFilterWish

    //Local Storage for SharedViewModel restore states
    data class UpdateSharedStorageByParam(val selectedName: String, val selectedMailingMode: String) :
        CreationFilterWish

    data class UpdateSharedStorageBySelection(
        val students: Set<Student>,
        val selectedStudents: Set<String>
    ) : CreationFilterWish

    data class UpdateSharedStorageBySelectionAttributing(
        val searchAttributes: Set<Attribute>,
        val searchSelectedAttributes: Set<Attribute>,
        val searchSelectedSearchSection: Section?
    ) : CreationFilterWish

    object ClearSharedStorage : CreationFilterWish
    data class SetSharedStorage(val filter: Filter) : CreationFilterWish
}
