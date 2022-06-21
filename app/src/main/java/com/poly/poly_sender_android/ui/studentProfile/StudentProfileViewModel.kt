package com.poly.poly_sender_android.ui.studentProfile

import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.studentProfile.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StudentProfileViewModel @Inject constructor() :
    BaseViewModel<StudentProfileState, StudentProfileWish, StudentProfileEffect, StudentProfileNews>() {

    private val initState = StudentProfileState(
        student = null
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<StudentProfileWish?>()
    override val effectFlow = MutableSharedFlow<StudentProfileEffect?>()
    override val newsFlow = MutableSharedFlow<StudentProfileNews>()

    @Inject
    override lateinit var store: StudentProfileStore
}