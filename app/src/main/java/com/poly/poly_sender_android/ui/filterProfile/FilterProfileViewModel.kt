package com.poly.poly_sender_android.ui.filterProfile

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.filterProfile.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FilterProfileViewModel @Inject constructor(
    override val logger: Logger,
    override val store: FilterProfileStore
) :
    BaseViewModel<FilterProfileState, FilterProfileWish, FilterProfileEffect, FilterProfileNews>() {

    private val initState = FilterProfileState(
        isLoading = false,
        filter = null,
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<FilterProfileWish?>()
    override val effectFlow = MutableSharedFlow<FilterProfileEffect?>()
    override val newsFlow = MutableSharedFlow<FilterProfileNews>()
}