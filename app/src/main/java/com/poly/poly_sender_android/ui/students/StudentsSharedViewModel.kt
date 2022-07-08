package com.poly.poly_sender_android.ui.students

import androidx.navigation.findNavController
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.mvi.Store
import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.students.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class StudentsSharedViewModel @Inject constructor(
    override val logger: Logger,
    override val store: StudentsStore
) :
    BaseViewModel<StudentsState, StudentsWish, StudentsEffect, StudentsNews>() {

    private val initState = StudentsState(
        label = "Students",//App.mCurrentActivity.findNavController(R.id.nav_host_fragment_content_main).graph.label.toString(),
        isLoading = false,
        students = emptySet(),
        selectedStudents = emptySet(),
        searchAttributes = emptySet(),
        searchSelectedAttributes = emptySet(),
        searchSections = emptySet(),
        searchSelectedSection = null
    )
    override val stateFlow = MutableStateFlow(initState)
    val nmState: StudentsState
        get() {
            return stateFlow.value
        }
    override val wishFlow = MutableSharedFlow<StudentsWish?>()
    override val effectFlow = MutableSharedFlow<StudentsEffect?>()
    override val newsFlow = MutableSharedFlow<StudentsNews>()
}