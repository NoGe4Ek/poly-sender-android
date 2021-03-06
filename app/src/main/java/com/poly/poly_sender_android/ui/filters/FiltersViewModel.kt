package com.poly.poly_sender_android.ui.filters

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.filters.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor(
    override val logger: Logger,
    override val store: FiltersStore
) :
    BaseViewModel<FiltersState, FiltersWish, FiltersEffect, FiltersNews>() {

    private val initState = FiltersState(
        isLoading = false,
        filters = emptySet(),
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<FiltersWish?>()
    override val effectFlow = MutableSharedFlow<FiltersEffect?>()
    override val newsFlow = MutableSharedFlow<FiltersNews>()
}