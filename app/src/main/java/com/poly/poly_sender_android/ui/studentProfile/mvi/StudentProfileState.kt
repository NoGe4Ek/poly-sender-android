package com.poly.poly_sender_android.ui.studentProfile.mvi

import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.State

data class StudentProfileState(
    val student: Student?
) : State