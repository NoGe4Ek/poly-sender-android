package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.*
import javax.inject.Inject

class StudentsStore @Inject constructor(
): Store<StudentsState, StudentsWish, StudentsEffect, StudentsNews>() {
    init {
        actor = StudentsActor()
        reducer = StudentsReducer()
    }
}