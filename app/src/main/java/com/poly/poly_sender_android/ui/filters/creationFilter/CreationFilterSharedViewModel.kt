package com.poly.poly_sender_android.ui.filters.creationFilter

import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.*
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreationFilterSharedViewModel @Inject constructor() :
    BaseViewModel<CreationFilterState, CreationFilterWish, CreationFilterEffect, CreationFilterNews>() {
    companion object {
        val initState = CreationFilterState(
            label = "Selection",
            isLoading = false,
            isEdit = false,
            editableFilter = null,
            selectedName = "",
            selectedMailingMode = "",
            mailingModes = emptySet(),
            students = emptySet(),
            selectedStudents = emptySet(),
            searchAttributes = emptySet(),
            searchSelectedAttributes = emptySet(),
            searchSelectedSection = null,
            sections = emptySet(),
        )
    }

    override val stateFlow = MutableStateFlow(initState)
    val nmState: CreationFilterState
        get() {
            return stateFlow.value
        }
    override val wishFlow = MutableSharedFlow<CreationFilterWish?>()
    override val effectFlow = MutableSharedFlow<CreationFilterEffect?>()
    override val newsFlow = MutableSharedFlow<CreationFilterNews>()

    @Inject
    override lateinit var store: CreationFilterStore
}