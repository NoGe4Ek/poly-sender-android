package com.poly.poly_sender_android.ui.studentProfile.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class StudentProfileStore @Inject constructor(
): Store<StudentProfileState, StudentProfileWish, StudentProfileEffect, StudentProfileNews>() {
    init {
        actor = StudentProfileActor()
        reducer = StudentProfileReducer()
    }
}