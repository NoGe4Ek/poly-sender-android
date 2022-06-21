package com.poly.poly_sender_android.ui.attributes.creationSection

import com.poly.poly_sender_android.ui.attributes.mvi.*
import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.attributes.SearchParam
import com.poly.poly_sender_android.ui.attributes.creationSection.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreationSectionViewModel @Inject constructor(): BaseViewModel<CreationSectionState, CreationSectionWish, CreationSectionEffect, CreationSectionNews>() {

    private val initState = CreationSectionState(
        isLoading = false,
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<CreationSectionWish?>()
    override val effectFlow = MutableSharedFlow<CreationSectionEffect?>()
    override val newsFlow = MutableSharedFlow<CreationSectionNews>()

    @Inject override lateinit var store: CreationSectionStore
}