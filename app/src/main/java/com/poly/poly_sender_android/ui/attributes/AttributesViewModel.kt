package com.poly.poly_sender_android.ui.attributes

import com.poly.poly_sender_android.ui.attributes.mvi.*
import com.poly.poly_sender_android.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AttributesViewModel @Inject constructor(): BaseViewModel<AttributesState, AttributesWish, AttributesEffect, AttributesNews>() {

    private val initState = AttributesState(
        isLoading = false,
        attributes = emptyList(),
        searchParam = SearchParam(),
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<AttributesWish?>()
    override val effectFlow = MutableSharedFlow<AttributesEffect?>()
    override val newsFlow = MutableSharedFlow<AttributesNews>()

    @Inject override lateinit var store: AttributesStore
}