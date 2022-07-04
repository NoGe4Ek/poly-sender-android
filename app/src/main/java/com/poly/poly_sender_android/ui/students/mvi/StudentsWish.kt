package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Wish
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish

sealed interface StudentsWish : Wish {
    data class RefreshStudents(val searchSelectedAttributes: Set<Attribute>, val query: String) :
        StudentsWish

    data class RefreshSearchingAttributesBySelectedSection(val selectedSearchSection: Section?) :
        StudentsWish

    object RefreshSections : StudentsWish
    data class RefreshSelectedSection(val section: Section?) : StudentsWish

    object ClearSearchParam :
        StudentsWish // delete selected attributes + reload all students(not selected)

    data class SelectStudent(val student: String): StudentsWish
    data class SelectStudents(val students: Set<String>): StudentsWish
    data class DismissStudent(val student: String): StudentsWish
    data class DismissStudents(val students: Set<String>): StudentsWish

    data class SelectAttribute(val attribute: Attribute): StudentsWish
    data class DismissAttribute(val attribute: Attribute): StudentsWish
}