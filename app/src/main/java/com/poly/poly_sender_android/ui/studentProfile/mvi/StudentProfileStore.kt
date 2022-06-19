package com.poly.poly_sender_android.ui.studentProfile.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class StudentProfileStore @Inject constructor(
    logger: Logger
): Store<StudentProfileState, UserDetailsWish, StudentProfileEffect, StudentProfileNews>(logger) {
    init {
        actor = StudentProfileActor()
        reducer = StudentProfileReducer()
    }
}