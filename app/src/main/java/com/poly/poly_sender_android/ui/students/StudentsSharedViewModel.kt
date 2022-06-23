package com.poly.poly_sender_android.ui.students

import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.*
import com.poly.poly_sender_android.ui.students.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class StudentsSharedViewModel @Inject constructor() :
    BaseViewModel<StudentsState, StudentsWish, StudentsEffect, StudentsNews>() {

    private val initState = StudentsState(
        isLoading = false,
        students = emptyList(),
        selectedStudents = emptyList(),
        searchAttributes = emptyList(),
        searchSelectedAttributes = emptyList(),
        searchSelectedSection = ""
    )
    override val stateFlow = MutableStateFlow(initState)
    val nmState: StudentsState
        get() {
            return stateFlow.value
        }
    override val wishFlow = MutableSharedFlow<StudentsWish?>()
    override val effectFlow = MutableSharedFlow<StudentsEffect?>()
    override val newsFlow = MutableSharedFlow<StudentsNews>()

    @Inject
    override lateinit var store: StudentsStore
}