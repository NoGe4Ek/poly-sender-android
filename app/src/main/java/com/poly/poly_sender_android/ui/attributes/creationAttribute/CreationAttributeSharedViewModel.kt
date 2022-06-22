package com.poly.poly_sender_android.ui.attributes.creationAttribute

import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class CreationAttributeSharedViewModel @Inject constructor() :
    BaseViewModel<CreationAttributeState, CreationAttributeWish, CreationAttributeEffect, CreationAttributeNews>() {

    private val initState = CreationAttributeState(
        isLoading = false,
        selectedName = "",
        selectedSection = "",
        students = emptyList(),
        selectedStudents = emptyList(),
        searchAttributes = emptyList(),
        searchSelectedAttributes = emptyList(),
        searchSelectedSection = ""
    )
    override val stateFlow = MutableStateFlow(initState)
    val nmState: CreationAttributeState
        get() {
            return stateFlow.value
        }
    override val wishFlow = MutableSharedFlow<CreationAttributeWish?>()
    override val effectFlow = MutableSharedFlow<CreationAttributeEffect?>()
    override val newsFlow = MutableSharedFlow<CreationAttributeNews>()

    @Inject
    override lateinit var store: CreationAttributeStore
}