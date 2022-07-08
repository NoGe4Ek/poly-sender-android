package com.poly.poly_sender_android.ui.attributes

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.ui.attributes.mvi.*
import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.students.mvi.StudentsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AttributesViewModel @Inject constructor(
    override val logger: Logger,
    override val store: AttributesStore
) :
    BaseViewModel<AttributesState, AttributesWish, AttributesEffect, AttributesNews>() {

    private val initState = AttributesState(
        isLoading = false,
        attributes = emptySet(),
        searchSections = emptySet(),
        searchSelectedSection = null,
    )
    override val stateFlow = MutableStateFlow(initState)
    val nmState: AttributesState
        get() {
            return stateFlow.value
        }
    override val wishFlow = MutableSharedFlow<AttributesWish?>()
    override val effectFlow = MutableSharedFlow<AttributesEffect?>()
    override val newsFlow = MutableSharedFlow<AttributesNews>()
}