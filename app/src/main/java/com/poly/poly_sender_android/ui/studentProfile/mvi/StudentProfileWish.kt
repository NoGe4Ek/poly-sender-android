package com.poly.poly_sender_android.ui.studentProfile.mvi

import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Wish

sealed interface StudentProfileWish: Wish {
    data class SetStudent(val student: Student): StudentProfileWish
}
