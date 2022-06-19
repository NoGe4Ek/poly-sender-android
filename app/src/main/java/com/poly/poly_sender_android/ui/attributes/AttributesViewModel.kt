package com.poly.poly_sender_android.ui.attributes

import com.poly.poly_sender_android.ui.attributes.mvi.*
import com.poly.poly_sender_android.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AttributesViewModel @Inject constructor(): BaseViewModel<CreationAttributeState, CreationAttributeWish, CreationAttributeEffect, CreationAttributeNews>() {

    private val initState = CreationAttributeState(
        isLoading = false,
        users = emptyList(),
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<CreationAttributeWish?>()
    override val effectFlow = MutableSharedFlow<CreationAttributeEffect?>()
    override val newsFlow = MutableSharedFlow<CreationAttributeNews>()

    @Inject override lateinit var store: CreationAttributeStore
}