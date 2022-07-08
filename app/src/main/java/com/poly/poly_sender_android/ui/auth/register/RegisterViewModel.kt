package com.poly.poly_sender_android.ui.auth.register

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.auth.register.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    override val logger: Logger,
    override val store: RegisterStore
) :
    BaseViewModel<RegisterState, RegisterWish, RegisterEffect, RegisterNews>() {

    private val initState = RegisterState(
        isLoading = false,
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<RegisterWish?>()
    override val effectFlow = MutableSharedFlow<RegisterEffect?>()
    override val newsFlow = MutableSharedFlow<RegisterNews>()
}