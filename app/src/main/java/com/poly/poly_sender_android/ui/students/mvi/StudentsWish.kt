package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Wish
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish

sealed interface StudentsWish : Wish {
    object FetchLocalUser: StudentsWish

    data class RefreshStudents(val searchSelectedAttributes: Set<Attribute>) :
        StudentsWish

    data class RefreshSearchingAttributesBySelectedSection(val selectedSearchSection: String) :
        StudentsWish

    object ClearSearchParam :
        StudentsWish // delete selected attributes + reload all students(not selected)

    data class SelectStudent(val student: Student): StudentsWish
    data class DismissStudent(val student: Student): StudentsWish

    data class SelectAttribute(val attribute: Attribute): StudentsWish
    data class DismissAttribute(val attribute: Attribute): StudentsWish
}