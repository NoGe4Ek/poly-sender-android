package com.poly.poly_sender_android.ui.auth.restore

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.auth.restore.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RestoreViewModel @Inject constructor(
    override val logger: Logger,
    override val store: RestoreStore
): BaseViewModel<RestoreState, RestoreWish, RestoreEffect, RestoreNews>() {

    private val initState = RestoreState(
        isLoading = false,
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<RestoreWish?>()
    override val effectFlow = MutableSharedFlow<RestoreEffect?>()
    override val newsFlow = MutableSharedFlow<RestoreNews>()
}